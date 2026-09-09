package com.buscapecas.app.http.dto;

public record LoginResponse(
        Long id,
        String nome,
        String email,
        String plano
) {
}