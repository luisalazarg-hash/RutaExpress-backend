package com.rutaexpress.ms_notificaciones.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class NotificacionEvento implements Serializable {
    private UUID envioId;
    private String destinatario;
    private String mensaje;
    private String canal;
    private LocalDateTime fecha;

    public NotificacionEvento() {
    }

    public NotificacionEvento(UUID envioId, String destinatario, String mensaje, String canal, LocalDateTime fecha) {
        this.envioId = envioId;
        this.destinatario = destinatario;
        this.mensaje = mensaje;
        this.canal = canal;
        this.fecha = fecha;
    }

    public UUID getEnvioId() { return envioId; }
    public void setEnvioId(UUID envioId) { this.envioId = envioId; }
    public String getDestinatario() { return destinatario; }
    public void setDestinatario(String destinatario) { this.destinatario = destinatario; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public String getCanal() { return canal; }
    public void setCanal(String canal) { this.canal = canal; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}
