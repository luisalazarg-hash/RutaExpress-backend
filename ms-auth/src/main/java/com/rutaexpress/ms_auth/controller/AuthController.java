package com.rutaexpress.ms_auth.controller;

import com.rutaexpress.ms_auth.dto.UsuarioResponse;
import com.rutaexpress.ms_auth.service.UsuarioService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            @AuthenticationPrincipal Jwt jwt
    ) {

        String oid = jwt.getClaimAsString("oid");
        String tid = jwt.getClaimAsString("tid");

        if (oid == null || tid == null) {
            throw new IllegalArgumentException(
                    "El token no contiene los identificadores oid y tid"
            );
        }

        return usuarioService.obtenerUsuarioActual(
                UUID.fromString(oid),
                UUID.fromString(tid)
        );
    }
}