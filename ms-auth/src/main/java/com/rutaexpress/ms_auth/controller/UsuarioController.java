package com.rutaexpress.ms_auth.controller;

import com.rutaexpress.ms_auth.dto.CrearUsuarioRequest;
import com.rutaexpress.ms_auth.dto.UsuarioResponse;
import com.rutaexpress.ms_auth.service.UsuarioService;
import com.rutaexpress.ms_auth.dto.ActualizarEstadoUsuarioRequest;
import com.rutaexpress.ms_auth.dto.ActualizarRolUsuarioRequest;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(
            UsuarioService usuarioService
    ) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @PreAuthorize(
        "@autorizacionService.tieneRol(authentication, T(com.rutaexpress.ms_auth.model.Rol).ADMIN)"
    )
    public List<UsuarioResponse> listarUsuarios() {

        return usuarioService.listarUsuarios();
    }

    @GetMapping("/{id}")
    @PreAuthorize(
        "@autorizacionService.tieneRol(authentication, T(com.rutaexpress.ms_auth.model.Rol).ADMIN)"
    )
    public UsuarioResponse obtenerUsuarioPorId(
            @PathVariable Long id
    ) {

        return usuarioService.obtenerUsuarioPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize(
        "@autorizacionService.tieneRol(authentication, T(com.rutaexpress.ms_auth.model.Rol).ADMIN)"
    )
    public UsuarioResponse crearUsuario(
            @Valid @RequestBody CrearUsuarioRequest request
    ) {

        return usuarioService.crearUsuario(request);
    }

    @PatchMapping("/{id}/estado")
    @PreAuthorize(
        "@autorizacionService.tieneRol(authentication, T(com.rutaexpress.ms_auth.model.Rol).ADMIN)"
    )
    public UsuarioResponse actualizarEstado(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarEstadoUsuarioRequest request
    ) {

        return usuarioService.actualizarEstado(
                id,
                request.estado()
        );
    }

    @PatchMapping("/{id}/rol")
    @PreAuthorize(
        "@autorizacionService.tieneRol(authentication, T(com.rutaexpress.ms_auth.model.Rol).ADMIN)"
    )
    public UsuarioResponse actualizarRol(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarRolUsuarioRequest request
    ) {

        return usuarioService.actualizarRol(
                id,
                request.rol(),
                request.empresaId()
        );
    }

}