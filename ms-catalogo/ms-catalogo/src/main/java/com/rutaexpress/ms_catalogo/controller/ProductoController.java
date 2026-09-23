package com.rutaexpress.ms_catalogo.controller;

import com.rutaexpress.ms_catalogo.model.Producto;
import com.rutaexpress.ms_catalogo.service.ProductoService;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/catalog")
public class ProductoController {
    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Producto> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtener(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(service.obtener(id));
        } catch (java.util.NoSuchElementException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Producto> crear(@RequestBody ProductRequest request) {
        Producto producto = new Producto(request.name(), request.description(), request.price(), request.stock());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(producto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(
            @PathVariable UUID id,
            @RequestBody ProductRequest request) {
        try {
            Producto producto = service.obtener(id);
            producto.setNombre(request.name());
            producto.setDescripcion(request.description());
            producto.setPrecio(request.price());
            producto.setStock(request.stock());
            return ResponseEntity.ok(service.guardar(producto));
        } catch (java.util.NoSuchElementException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        try {
            Producto producto = service.obtener(id);
            producto.setActivo(false);
            service.guardar(producto);
            return ResponseEntity.noContent().build();
        } catch (java.util.NoSuchElementException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    public record ProductRequest(
            String name,
            String description,
            BigDecimal price,
            Integer stock) {
    }
}