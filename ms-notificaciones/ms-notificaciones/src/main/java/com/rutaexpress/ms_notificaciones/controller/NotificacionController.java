package com.rutaexpress.ms_notificaciones.controller;

import com.rutaexpress.ms_notificaciones.model.CanalNotificacion;
import com.rutaexpress.ms_notificaciones.model.Notificacion;
import com.rutaexpress.ms_notificaciones.service.NotificacionService;
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
@RequestMapping("/api/notifications")
public class NotificacionController {
    private final NotificacionService service;

    public NotificacionController(NotificacionService service) {
        this.service = service;
    }

    @GetMapping("/shipment/{envioId}")
    public List<Notificacion> listarPorEnvio(@PathVariable UUID envioId) {
        return service.listarPorEnvio(envioId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notificacion> obtener(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(service.obtener(id));
        } catch (java.util.NoSuchElementException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Notificacion> crear(@RequestBody NotificationRequest request) {
        Notificacion notificacion = new Notificacion(
                request.shipmentId(),
                request.recipient(),
                request.message(),
                request.channel());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(notificacion));
    }

    public record NotificationRequest(
            UUID shipmentId,
            String recipient,
            String message,
            CanalNotificacion channel) {
    }
}