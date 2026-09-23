package com.rutaexpress.ms_envios.controller;

import com.rutaexpress.ms_envios.model.Envio;
import com.rutaexpress.ms_envios.model.EstadoEnvio;
import com.rutaexpress.ms_envios.service.EnvioService;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shipments")
public class EnvioController {
    private final EnvioService service;

    public EnvioController(EnvioService service) {
        this.service = service;
    }

    @GetMapping
    public List<ShipmentResponse> listar(@RequestParam(required = false) EstadoEnvio status) {
        List<Envio> envios = status == null ? service.listar() : service.listarPorEstado(status);
        return envios.stream().map(ShipmentResponse::from).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShipmentResponse> obtener(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(ShipmentResponse.from(service.obtener(id)));
        } catch (java.util.NoSuchElementException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ShipmentResponse> crear(@RequestBody CreateShipmentRequest request) {
        Envio envio = new Envio(
                request.productId(),
                request.recipientName(),
                request.originAddress(),
                request.destinationAddress());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ShipmentResponse.from(service.guardar(envio)));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ShipmentResponse> cambiarEstado(
            @PathVariable UUID id,
            @RequestBody UpdateStatusRequest request) {
        try {
            return ResponseEntity.ok(ShipmentResponse.from(service.cambiarEstado(id, request.status())));
        } catch (java.util.NoSuchElementException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        try {
            service.obtener(id);
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (java.util.NoSuchElementException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    public record CreateShipmentRequest(
            UUID productId,
            String recipientName,
            String originAddress,
            String destinationAddress) {
    }

    public record UpdateStatusRequest(EstadoEnvio status) {
    }

    public record ShipmentResponse(
            UUID id,
            UUID productId,
            String recipientName,
            String originAddress,
            String destinationAddress,
            EstadoEnvio status,
            Instant createdAt,
            Instant updatedAt) {
        static ShipmentResponse from(Envio envio) {
            return new ShipmentResponse(
                    envio.getId(),
                    envio.getProductoId(),
                    envio.getDestinatario(),
                    envio.getDireccionOrigen(),
                    envio.getDireccionDestino(),
                    envio.getEstado(),
                    envio.getCreadoEn(),
                    envio.getActualizadoEn());
        }
    }
}