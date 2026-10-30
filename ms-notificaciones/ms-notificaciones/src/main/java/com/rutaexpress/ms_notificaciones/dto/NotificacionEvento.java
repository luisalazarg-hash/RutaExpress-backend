package com.rutaexpress.ms_notificaciones.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public class NotificacionEvento implements Serializable {
    private String type;
    private String eventId;
    private Instant timestamp;
    private String traceId;
    private String correlationId;
    private String eventType;
    private UUID envioId;
    private String destinatario;
    private String mensaje;
    private String canal;
    private String estadoAnterior;
    private String estadoNuevo;
    private Instant fecha;

    public NotificacionEvento() {
    }

    public NotificacionEvento(UUID envioId, String destinatario, String mensaje, String canal, Instant fecha) {
        this.envioId = envioId;
        this.destinatario = destinatario;
        this.mensaje = mensaje;
        this.canal = canal;
        this.fecha = fecha;
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }
    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
    public String getTraceId() { return traceId; }
    public void setTraceId(String traceId) { this.traceId = traceId; }
    public String getCorrelationId() { return correlationId; }
    public void setCorrelationId(String correlationId) { this.correlationId = correlationId; }
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    public UUID getEnvioId() { return envioId; }
    public void setEnvioId(UUID envioId) { this.envioId = envioId; }
    public String getDestinatario() { return destinatario; }
    public void setDestinatario(String destinatario) { this.destinatario = destinatario; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public String getCanal() { return canal; }
    public void setCanal(String canal) { this.canal = canal; }
    public String getEstadoAnterior() { return estadoAnterior; }
    public void setEstadoAnterior(String estadoAnterior) { this.estadoAnterior = estadoAnterior; }
    public String getEstadoNuevo() { return estadoNuevo; }
    public void setEstadoNuevo(String estadoNuevo) { this.estadoNuevo = estadoNuevo; }
    public Instant getFecha() { return fecha; }
    public void setFecha(Instant fecha) { this.fecha = fecha; }
}
