package com.buscapecas.app.services;

import java.time.YearMonth;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buscapecas.app.exceptions.ApiKeyInvalidaException;
import com.buscapecas.app.exceptions.NaoAutenticadoException;
import com.buscapecas.app.exceptions.RateLimitExcedidoException;
import com.buscapecas.app.models.Usuario;
import com.buscapecas.app.repositories.UsuarioRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class RateLimitService {

    private static final int LIMITE_ANONIMO_MENSAL = 10;

    private static final String SESSION_MES_ANONIMO =
            "mesRateLimitAnonimo";

    private static final String SESSION_REQUISICOES_ANONIMAS =
            "requisicoesAnonimas";

    private final UsuarioRepository usuarioRepository;

    public RateLimitService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Verifica o limite de um usuário autenticado.
     *
     * BASICO = 500 requisições/mês
     * PREMIUM = 5000 requisições/mês
     */
    @Transactional
    public void verificarPorUsuarioId(Long usuarioId) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(NaoAutenticadoException::new);

        if (!usuario.podeFazerRequisicao()) {
            throw new RateLimitExcedidoException();
        }

        usuario.registrarRequisicao();

        usuarioRepository.save(usuario);
    }

    /**
     * Verifica o limite de usuários anônimos.
     *
     * Usuários sem conta possuem 10 requisições
     * gratuitas por mês.
     *
     * O contador fica armazenado na sessão HTTP.
     */
    public void verificarAnonimo(HttpSession session) {

        String mesAtual = YearMonth.now().toString();

        String mesDaSessao =
                (String) session.getAttribute(SESSION_MES_ANONIMO);

        Integer requisicoes =
                (Integer) session.getAttribute(SESSION_REQUISICOES_ANONIMAS);

        if (mesDaSessao == null ||
                !mesAtual.equals(mesDaSessao)) {

            requisicoes = 0;

            session.setAttribute(
                    SESSION_MES_ANONIMO,
                    mesAtual
            );
        }

        if (requisicoes == null) {
            requisicoes = 0;
        }

        if (requisicoes >= LIMITE_ANONIMO_MENSAL) {
            throw new RateLimitExcedidoException();
        }

        requisicoes++;

        session.setAttribute(
                SESSION_REQUISICOES_ANONIMAS,
                requisicoes
        );
    }

    /**
     * Rate limit para consumidores externos que utilizam API Key.
     *
     * Mantido separado da autenticação por sessão.
     */
    @Transactional
    public void verificarPorApiKey(String apiKey) {

        if (apiKey == null || apiKey.isBlank()) {
            throw new ApiKeyInvalidaException();
        }

        UUID chave;

        try {
            chave = UUID.fromString(apiKey);
        } catch (IllegalArgumentException e) {
            throw new ApiKeyInvalidaException();
        }

        Usuario usuario = usuarioRepository.findByApiKey(chave)
                .orElseThrow(ApiKeyInvalidaException::new);

        if (!usuario.podeFazerRequisicao()) {
            throw new RateLimitExcedidoException();
        }

        usuario.registrarRequisicao();

        usuarioRepository.save(usuario);
    }
}