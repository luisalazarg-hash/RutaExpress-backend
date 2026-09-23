package com.rutaexpress.ms_auth.controller;

import com.rutaexpress.ms_auth.model.Empresa;
import com.rutaexpress.ms_auth.service.EmpresaService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/companies")
public class EmpresaController {
    private final EmpresaService service;

    public EmpresaController(EmpresaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Empresa> listar() {
        return service.listarEmpresas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empresa> obtener(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.obtenerEmpresaActiva(id));
        } catch (IllegalArgumentException | IllegalStateException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}