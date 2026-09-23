package com.rutaexpress.ms_auth.dto;

import com.rutaexpress.ms_auth.model.Rol;
import jakarta.validation.constraints.NotNull;

public record ActualizarRolUsuarioRequest(

        @NotNull
        Rol rol,

        Long empresaId

) {
}