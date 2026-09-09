package com.buscapecas.app.http.dto;

import com.buscapecas.app.models.TipoPlano;
import com.buscapecas.app.models.Usuario;
import com.buscapecas.app.services.PasswordService;

public record CriarUsuarioRequest(
        String nome,
        String email,
        String senha,
        TipoPlano plano
) {

    public CriarUsuarioRequest {

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(
                    "O nome é obrigatório."
            );
        }

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

        if (plano == null) {
            plano = TipoPlano.BASICO;
        }
    }

    public Usuario paraNovaEntidade(
            PasswordService passwordService) {

        Usuario usuario = new Usuario();

        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(
                passwordService.gerarHash(senha)
        );
        usuario.setPlano(plano);

        return usuario;
    }
}