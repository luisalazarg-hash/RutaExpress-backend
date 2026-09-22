package com.rutaexpress.ms_reportes.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "reportes")
public class Reporte {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nombre;
    private String descripcion;
    private Instant generadoEn;

    protected Reporte() {}

    public Reporte(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.generadoEn = Instant.now();
    }

    public UUID getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Instant getGeneradoEn() { return generadoEn; }
}
