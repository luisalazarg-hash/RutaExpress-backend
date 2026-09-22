package com.rutaexpress.ms_auth.repository;

import com.rutaexpress.ms_auth.model.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository
        extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByEntraOidAndEntraTid(
            UUID entraOid,
            UUID entraTid
    );

    boolean existsByEmail(String email);

    boolean existsByEntraOidAndEntraTid(
            UUID entraOid,
            UUID entraTid
    );
}