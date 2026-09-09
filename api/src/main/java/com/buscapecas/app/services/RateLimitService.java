package com.buscapecas.app.services;

import java.time.YearMonth;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buscapecas.app.exceptions.ApiKeyInvalidaException;
import com.buscapecas.app.exceptions.NaoAutenticadoException;
import com.buscapecas.app.exceptions.RateLimitExcedidoException;
import com.buscapecas.app.models.Usuario;
import com.buscapecas.app.repositories.UsuarioRepository;

@Service
public class RateLimitService {

    private static final int LIMITE_ANONIMO_MENSAL = 10;

    private final UsuarioRepository usuarioRepository;

    /**
     * Contador de anônimos, por identificador (hoje o IP), em memória.
     * Zera quando a aplicação reinicia — aceitável, já que é só um limite de
     * cortesia antes do cadastro.
     */
    private final Map<String, ContadorAnonimo> contadoresAnonimos =
            new ConcurrentHashMap<>();

    private record ContadorAnonimo(String mes, int requisicoes) {
    }

    public RateLimitService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

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

    public void verificarAnonimo(String identificador) {

        String mesAtual = YearMonth.now().toString();

        boolean[] excedeu = { false };

        contadoresAnonimos.compute(identificador, (chave, atual) -> {

            int usadas = (atual == null || !mesAtual.equals(atual.mes()))
                    ? 0
                    : atual.requisicoes();

            if (usadas >= LIMITE_ANONIMO_MENSAL) {
                excedeu[0] = true;
                return new ContadorAnonimo(mesAtual, usadas);
            }

            return new ContadorAnonimo(mesAtual, usadas + 1);
        });

        if (excedeu[0]) {
            throw new RateLimitExcedidoException();
        }
    }

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
