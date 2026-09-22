package com.rutaexpress.ms_notificaciones.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "notificaciones")
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID envioId;
    private String destinatario;
    private String mensaje;
    @Enumerated(EnumType.STRING)
    private CanalNotificacion canal;
    private boolean enviada;
    private Instant creadoEn;

    protected Notificacion() {}

    public Notificacion(UUID envioId, String destinatario, String mensaje, CanalNotificacion canal) {
        this.envioId = envioId;
        this.destinatario = destinatario;
        this.mensaje = mensaje;
        this.canal = canal;
    }

    @PrePersist
    void inicializar() { creadoEn = Instant.now(); }

    public UUID getId() { return id; }
    public UUID getEnvioId() { return envioId; }
    public void setEnvioId(UUID envioId) { this.envioId = envioId; }
    public String getDestinatario() { return destinatario; }
    public void setDestinatario(String destinatario) { this.destinatario = destinatario; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public CanalNotificacion getCanal() { return canal; }
    public void setCanal(CanalNotificacion canal) { this.canal = canal; }
    public boolean isEnviada() { return enviada; }
    public void setEnviada(boolean enviada) { this.enviada = enviada; }
    public Instant getCreadoEn() { return creadoEn; }
}
