package com.rutaexpress.ms_auth.controller;

import com.rutaexpress.ms_auth.dto.UsuarioResponse;
import com.rutaexpress.ms_auth.exception.AccesoDenegadoException;
import com.rutaexpress.ms_auth.model.Rol;
import com.rutaexpress.ms_auth.service.UsuarioService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/me")
    public UsuarioResponse obtenerUsuarioActual(
            @AuthenticationPrincipal Jwt jwt) {

        String oid = jwt.getClaimAsString("oid");
        String tid = jwt.getClaimAsString("tid");

        if (oid == null || tid == null) {
            throw new AccesoDenegadoException(
                    "El token no contiene los identificadores oid y tid"
            );
        }

        Rol rolEntra = obtenerRolEntra(jwt);

        return usuarioService.obtenerUsuarioActual(
                UUID.fromString(oid),
                UUID.fromString(tid),
                rolEntra
        );
    }

    @PostMapping("/vincular")
    public UsuarioResponse vincularUsuario(
            @AuthenticationPrincipal Jwt jwt) {

        String oid = jwt.getClaimAsString("oid");
        String tid = jwt.getClaimAsString("tid");
        String email = jwt.getClaimAsString("preferred_username");

        if (oid == null || tid == null) {
            throw new AccesoDenegadoException(
                    "El token no contiene los identificadores necesarios"
            );
        }

        Rol rolEntra = obtenerRolEntra(jwt);

        return usuarioService.vincularUsuarioEntra(
                UUID.fromString(oid),
                UUID.fromString(tid),
                email,
                rolEntra
        );
    }

    private Rol obtenerRolEntra(Jwt jwt) {

        List<String> roles = jwt.getClaimAsStringList("roles");

        if (roles == null || roles.isEmpty()) {
            throw new AccesoDenegadoException(
                    "El usuario no tiene un rol asignado en Microsoft Entra ID"
            );
        }

        if (roles.size() != 1) {
            throw new AccesoDenegadoException(
                    "El usuario debe tener exactamente un rol de RutaExpress"
            );
        }

        try {
            return Rol.valueOf(roles.get(0));
        } catch (IllegalArgumentException ex) {
            throw new AccesoDenegadoException(
                    "El rol recibido desde Microsoft Entra ID no es válido"
            );
        }
    }
}