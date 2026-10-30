package com.rutaexpress.ms_envios.service;

import com.rutaexpress.ms_envios.dto.ShipmentNotificationEvent;
import com.rutaexpress.ms_envios.model.Envio;
import com.rutaexpress.ms_envios.model.EstadoEnvio;
import java.time.Instant;
import java.util.UUID;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ShipmentEventPublisher {
    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String createdRoutingKey;
    private final String statusRoutingKey;
    private final String detailRoutingKey;

    public ShipmentEventPublisher(
            RabbitTemplate rabbitTemplate,
            @Value("${app.rabbitmq.exchange}") String exchange,
            @Value("${app.rabbitmq.routing-key.created}") String createdRoutingKey,
            @Value("${app.rabbitmq.routing-key.status-changed}") String statusRoutingKey,
            @Value("${app.rabbitmq.routing-key.detail:shipment.detail.generated}") String detailRoutingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.createdRoutingKey = createdRoutingKey;
        this.statusRoutingKey = statusRoutingKey;
        this.detailRoutingKey = detailRoutingKey;
    }

    public void pedidoCreado(Envio envio) {
        publicar(createdRoutingKey, envio, "shipment.created", detalleCreado(envio), null);
        publicar(detailRoutingKey, envio, "shipment.detail.generated", detalleCreado(envio), null);
    }

    public void estadoModificado(Envio envio, EstadoEnvio estadoAnterior) {
        publicar(statusRoutingKey, envio, "shipment.status.changed", detalleEstado(envio, estadoAnterior), estadoAnterior);
        publicar(detailRoutingKey, envio, "shipment.detail.generated", detalleEstado(envio, estadoAnterior), estadoAnterior);
    }

    private void publicar(String routingKey, Envio envio, String eventType, String mensaje,
                          EstadoEnvio estadoAnterior) {
        Instant now = Instant.now();
        UUID correlationId = UUID.randomUUID();
        rabbitTemplate.convertAndSend(exchange, routingKey, new ShipmentNotificationEvent(
                eventType,
                UUID.randomUUID(),
                now,
                correlationId.toString(),
                envio.getId().toString(),
                eventType,
                envio.getId(),
                envio.getProductoId(),
                envio.getDestinatario(),
                envio.getDireccionOrigen(),
                envio.getDireccionDestino(),
                mensaje,
                "EMAIL",
                estadoAnterior == null ? null : estadoAnterior.name(),
                envio.getEstado().name(),
                now));
    }

    private String detalleCreado(Envio envio) {
        return "Pedido " + envio.getId() + " creado. Destinatario: " + envio.getDestinatario()
                + ". Producto: " + envio.getProductoId() + ". Origen: " + envio.getDireccionOrigen()
                + ". Destino: " + envio.getDireccionDestino() + ". Estado: " + envio.getEstado() + ".";
    }

    private String detalleEstado(Envio envio, EstadoEnvio anterior) {
        return "Pedido " + envio.getId() + " actualizado de " + anterior + " a " + envio.getEstado()
                + ". Destinatario: " + envio.getDestinatario() + ". Producto: " + envio.getProductoId()
                + ". Origen: " + envio.getDireccionOrigen() + ". Destino: " + envio.getDireccionDestino() + ".";
    }
}
