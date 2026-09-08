package com.buscapecas.app.services;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buscapecas.app.exceptions.ApiKeyInvalidaException;
import com.buscapecas.app.exceptions.RateLimitExcedidoException;
import com.buscapecas.app.models.Usuario;
import com.buscapecas.app.repositories.UsuarioRepository;

@Service
public class RateLimitService {

    private final UsuarioRepository usuarioRepository;

    public RateLimitService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public void verificar(String apiKey) {

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