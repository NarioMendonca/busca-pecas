package com.buscapecas.app.services;

import org.springframework.stereotype.Service;

import com.buscapecas.app.exceptions.CredenciaisInvalidasException;
import com.buscapecas.app.http.dto.LoginRequest;
import com.buscapecas.app.http.dto.LoginResponse;
import com.buscapecas.app.models.Usuario;
import com.buscapecas.app.repositories.UsuarioRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class AuthService {

    public static final String SESSION_USUARIO_ID = "usuarioId";

    private final UsuarioRepository usuarioRepository;
    private final PasswordService passwordService;

    public AuthService(
            UsuarioRepository usuarioRepository,
            PasswordService passwordService) {

        this.usuarioRepository = usuarioRepository;
        this.passwordService = passwordService;
    }

    public LoginResponse login(
            LoginRequest request,
            HttpSession session) {

        Usuario usuario = usuarioRepository
                .findByEmail(request.email())
                .orElseThrow(CredenciaisInvalidasException::new);

        if (!passwordService.verificar(
                request.senha(),
                usuario.getSenha())) {

            throw new CredenciaisInvalidasException();
        }

        session.setAttribute(
                SESSION_USUARIO_ID,
                usuario.getId()
        );

        return new LoginResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPlano().name()
        );
    }
}