package com.buscapecas.app.http.dto;

import com.buscapecas.app.models.Usuario;

public record UsuarioResponse(
        Long id,
        String nome,
        String email,
        String plano,
        int limiteMensal,
        int requisicoesFeitasNoMes,
        int requisicoesRestantes
) {

    public static UsuarioResponse from(Usuario usuario) {

        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPlano().name(),
                usuario.getPlano().getLimiteMensal(),
                usuario.getRequisicoesFeitasNoMes(),
                usuario.getRequisicoesRestantes()
        );
    }
}