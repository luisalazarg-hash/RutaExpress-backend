package com.rutaexpress.ms_auth.controller;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/test")
    public Map<String, Object> test(
            @AuthenticationPrincipal Jwt jwt
    ) {

        return Map.of(
                "autenticado", true,
                "mensaje", "Token de Microsoft Entra ID válido",
                "oid", jwt.getClaimAsString("oid"),
                "tid", jwt.getClaimAsString("tid")
        );
    }
}