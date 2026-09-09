package com.buscapecas.app.http.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.buscapecas.app.http.dto.CriarUsuarioRequest;
import com.buscapecas.app.http.dto.UsuarioResponse;
import com.buscapecas.app.models.Usuario;
import com.buscapecas.app.repositories.UsuarioRepository;
import com.buscapecas.app.services.PasswordService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordService passwordService;

    public UsuarioController(
            UsuarioRepository usuarioRepository,
            PasswordService passwordService) {

        this.usuarioRepository = usuarioRepository;
        this.passwordService = passwordService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> criar(
            @RequestBody CriarUsuarioRequest request) {

        Usuario usuario =
                request.paraNovaEntidade(passwordService);

        Usuario usuarioSalvo =
                usuarioRepository.save(usuario);

        return ResponseEntity.ok(
                UsuarioResponse.from(usuarioSalvo)
        );
    }

    @GetMapping
    public List<UsuarioResponse> listar() {

        return usuarioRepository
                .findAll()
                .stream()
                .map(UsuarioResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscarPorId(
            @PathVariable Long id) {

        return usuarioRepository
                .findById(id)
                .map(UsuarioResponse::from)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }
}