package com.rutaexpress.ms_notificaciones.controller;

import com.rutaexpress.ms_notificaciones.model.CanalNotificacion;
import com.rutaexpress.ms_notificaciones.model.Notificacion;
import com.rutaexpress.ms_notificaciones.service.NotificacionProducer;
import com.rutaexpress.ms_notificaciones.service.NotificacionService;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public class NotificacionController {
    private final NotificacionService service;
    private final NotificacionProducer producer;

    public NotificacionController(NotificacionService service, NotificacionProducer producer) {
        this.service = service;
        this.producer = producer;
    }

    @GetMapping
    public List<Notificacion> listar(@RequestParam(required = false) UUID envioId) {
        return envioId == null ? service.listarTodos() : service.listarPorEnvio(envioId);
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

    @PostMapping("/simular")
    public ResponseEntity<Map<String, String>> simularEvento(@RequestBody SimularEventoRequest request) {
        producer.publicar(new com.rutaexpress.ms_notificaciones.dto.NotificacionEvento(
                request.shipmentId(),
                request.recipient(),
                request.message(),
                request.channel().name(),
                java.time.Instant.now()));

        return ResponseEntity.accepted().body(Map.of("mensaje", "Evento encolado correctamente."));
    }

    public record NotificationRequest(
            UUID shipmentId,
            String recipient,
            String message,
            CanalNotificacion channel) {
    }

    public record SimularEventoRequest(
            UUID shipmentId,
            String recipient,
            String message,
            CanalNotificacion channel) {
    }
}