package com.rutaexpress.ms_notificaciones.service;

import com.rutaexpress.ms_notificaciones.dto.NotificacionEvento;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NotificacionProducer {
    private final RabbitTemplate rabbitTemplate;

    @Value("${app.rabbitmq.exchange:cmd.topic}")
    private String exchangeName;

    @Value("${app.rabbitmq.routing-key.created:shipment.created}")
    private String defaultRoutingKey;

    public NotificacionProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publicar(NotificacionEvento evento) {
        publicar(evento, defaultRoutingKey);
    }

    public void publicar(NotificacionEvento evento, String routingKey) {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, evento);
    }
}
