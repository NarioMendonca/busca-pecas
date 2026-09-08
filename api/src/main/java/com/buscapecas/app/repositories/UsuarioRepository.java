package com.buscapecas.app.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buscapecas.app.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByApiKey(UUID apiKey);

    Optional<Usuario> findByEmail(String email);
}