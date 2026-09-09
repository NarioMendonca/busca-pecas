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
     * Verifica o limite de quem não está logado: 10 requisições por mês.
     *
     * A contagem é por IP, não por sessão HTTP. Sessão dependia do cliente
     * devolver o cookie JSESSIONID — quem chamasse via curl/Postman sem
     * guardar cookie ganhava uma sessão nova a cada request e nunca batia no
     * limite.
     */
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

    /**
     * Rate limit para consumidores externos que utilizam API Key.
     *
     * Mantido separado da autenticação por sessão. Hoje nenhum interceptor
     * chama este método — o RateLimitInterceptor usa sessão/IP. Só passa a
     * valer quando a API Key for exposta ao usuário e lida de um header.
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
