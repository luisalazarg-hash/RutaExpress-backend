package com.rutaexpress.ms_auth.dto;

import java.time.LocalDateTime;

public record ErrorResponse(
        int status,
        String error,
        String mensaje,
        String path,
        LocalDateTime timestamp
) {
}