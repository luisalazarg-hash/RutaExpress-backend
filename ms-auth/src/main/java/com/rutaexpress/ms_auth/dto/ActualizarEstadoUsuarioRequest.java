package com.rutaexpress.ms_auth.dto;

import com.rutaexpress.ms_auth.model.EstadoUsuario;
import jakarta.validation.constraints.NotNull;

public record ActualizarEstadoUsuarioRequest(

    @NotNull
    EstadoUsuario estado

) {
}