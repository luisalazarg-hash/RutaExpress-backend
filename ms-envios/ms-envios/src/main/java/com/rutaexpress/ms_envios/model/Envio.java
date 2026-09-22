package com.rutaexpress.ms_envios.model;

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
@Table(name = "envios")
public class Envio {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID productoId;
    private String destinatario;
    private String direccionOrigen;
    private String direccionDestino;
    @Enumerated(EnumType.STRING)
    private EstadoEnvio estado = EstadoEnvio.CREADO;
    private Instant creadoEn;
    private Instant actualizadoEn;

    protected Envio() {}

    public Envio(UUID productoId, String destinatario, String direccionOrigen, String direccionDestino) {
        this.productoId = productoId;
        this.destinatario = destinatario;
        this.direccionOrigen = direccionOrigen;
        this.direccionDestino = direccionDestino;
    }

    @PrePersist
    void inicializarFechas() {
        creadoEn = Instant.now();
        actualizadoEn = creadoEn;
    }

    public UUID getId() { return id; }
    public UUID getProductoId() { return productoId; }
    public void setProductoId(UUID productoId) { this.productoId = productoId; }
    public String getDestinatario() { return destinatario; }
    public void setDestinatario(String destinatario) { this.destinatario = destinatario; }
    public String getDireccionOrigen() { return direccionOrigen; }
    public void setDireccionOrigen(String direccionOrigen) { this.direccionOrigen = direccionOrigen; }
    public String getDireccionDestino() { return direccionDestino; }
    public void setDireccionDestino(String direccionDestino) { this.direccionDestino = direccionDestino; }
    public EstadoEnvio getEstado() { return estado; }
    public void setEstado(EstadoEnvio estado) { this.estado = estado; }
    public Instant getCreadoEn() { return creadoEn; }
    public Instant getActualizadoEn() { return actualizadoEn; }
}
