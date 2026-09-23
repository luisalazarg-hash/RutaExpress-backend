package com.rutaexpress.ms_auth.service;

import com.rutaexpress.ms_auth.model.Rol;
import com.rutaexpress.ms_auth.model.Usuario;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("autorizacionService")
public class AutorizacionService {

    private final UsuarioService usuarioService;

    public AutorizacionService(
            UsuarioService usuarioService
    ) {
        this.usuarioService = usuarioService;
    }

    public boolean tieneRol(
            Authentication authentication,
            Rol rol
    ) {

        if (authentication == null ||
                !(authentication.getPrincipal() instanceof Jwt jwt)) {

            return false;
        }

        String oid = jwt.getClaimAsString("oid");
        String tid = jwt.getClaimAsString("tid");

        if (oid == null || tid == null) {
            return false;
        }

        try {

            Usuario usuario =
                    usuarioService.obtenerUsuarioActivo(
                            UUID.fromString(oid),
                            UUID.fromString(tid)
                    );

            return usuario.getRol() == rol;

        } catch (RuntimeException ex) {
            return false;
        }
    }
}