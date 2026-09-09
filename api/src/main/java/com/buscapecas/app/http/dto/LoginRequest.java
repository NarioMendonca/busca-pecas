package com.buscapecas.app.http.dto;

public record LoginRequest(
        String email,
        String senha
) {

    public LoginRequest {

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException(
                    "O e-mail é obrigatório."
            );
        }

        if (senha == null || senha.isBlank()) {
            throw new IllegalArgumentException(
                    "A senha é obrigatória."
            );
        }
    }
}