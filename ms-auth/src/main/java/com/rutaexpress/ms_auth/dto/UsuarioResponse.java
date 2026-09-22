package com.rutaexpress.ms_auth.dto;

import com.rutaexpress.ms_auth.model.EstadoUsuario;
import com.rutaexpress.ms_auth.model.Rol;

import java.time.LocalDateTime;

public record UsuarioResponse(

        Long id,
        String nombre,
        String email,
        Rol rol,
        EstadoUsuario estado,
        Long empresaId,
        String nombreEmpresa,
        LocalDateTime fechaCreacion

) {
}