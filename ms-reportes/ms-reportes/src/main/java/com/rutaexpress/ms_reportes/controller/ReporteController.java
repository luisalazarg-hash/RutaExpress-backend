package com.rutaexpress.ms_reportes.controller;

import com.rutaexpress.ms_reportes.model.Reporte;
import com.rutaexpress.ms_reportes.service.ReporteService;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReporteController {
    private final ReporteService service;

    public ReporteController(ReporteService service) {
        this.service = service;
    }

    @GetMapping
    public List<Reporte> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reporte> obtener(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(service.obtener(id));
        } catch (java.util.NoSuchElementException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Reporte> crear(@RequestBody ReportRequest request) {
        Reporte reporte = new Reporte(request.name(), request.description());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(reporte));
    }

    public record ReportRequest(String name, String description) {
    }
}