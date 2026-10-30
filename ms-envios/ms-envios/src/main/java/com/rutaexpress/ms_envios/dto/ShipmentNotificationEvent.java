package com.rutaexpress.ms_envios.dto;

import java.time.Instant;
import java.util.UUID;

public record ShipmentNotificationEvent(
        String type,
        UUID eventId,
        Instant timestamp,
        String traceId,
        String correlationId,
        String eventType,
        UUID envioId,
        UUID productoId,
        String destinatario,
        String direccionOrigen,
        String direccionDestino,
        String mensaje,
        String canal,
        String estadoAnterior,
        String estadoNuevo,
        Instant fecha) {
}
