package com.rutaexpress.ms_envios.service;

import com.rutaexpress.ms_envios.dto.ShipmentNotificationEvent;
import com.rutaexpress.ms_envios.model.Envio;
import com.rutaexpress.ms_envios.model.EstadoEnvio;
import java.time.Instant;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ShipmentEventPublisher {
    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String createdRoutingKey;
    private final String statusRoutingKey;

    public ShipmentEventPublisher(
            RabbitTemplate rabbitTemplate,
            @Value("${app.rabbitmq.exchange}") String exchange,
            @Value("${app.rabbitmq.routing-key.created}") String createdRoutingKey,
            @Value("${app.rabbitmq.routing-key.status-changed}") String statusRoutingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.createdRoutingKey = createdRoutingKey;
        this.statusRoutingKey = statusRoutingKey;
    }

    public void pedidoCreado(Envio envio) {
        publicar(createdRoutingKey, new ShipmentNotificationEvent(
                "shipment.created", envio.getId(), envio.getProductoId(), envio.getDestinatario(),
                envio.getDireccionOrigen(), envio.getDireccionDestino(), detalleCreado(envio), "EMAIL",
                null, envio.getEstado().name(), Instant.now()));
    }

    public void estadoModificado(Envio envio, EstadoEnvio estadoAnterior) {
        publicar(statusRoutingKey, new ShipmentNotificationEvent(
                "shipment.status.changed", envio.getId(), envio.getProductoId(), envio.getDestinatario(),
                envio.getDireccionOrigen(), envio.getDireccionDestino(), detalleEstado(envio, estadoAnterior), "EMAIL",
                estadoAnterior.name(), envio.getEstado().name(), Instant.now()));
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

    private void publicar(String routingKey, ShipmentNotificationEvent evento) {
        rabbitTemplate.convertAndSend(exchange, routingKey, evento);
    }
}
