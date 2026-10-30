package com.rutaexpress.ms_notificaciones.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public class NotificacionEvento implements Serializable {
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
