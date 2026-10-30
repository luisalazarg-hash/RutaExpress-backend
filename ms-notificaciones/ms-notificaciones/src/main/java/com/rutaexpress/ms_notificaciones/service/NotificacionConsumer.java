package com.rutaexpress.ms_notificaciones.service;

import com.rutaexpress.ms_notificaciones.dto.NotificacionEvento;
import com.rutaexpress.ms_notificaciones.model.CanalNotificacion;
import com.rutaexpress.ms_notificaciones.model.Notificacion;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class NotificacionConsumer {
    private static final Logger log = LoggerFactory.getLogger(NotificacionConsumer.class);
    private final NotificacionService notificacionService;

    public NotificacionConsumer(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @RabbitListener(queues = "${app.rabbitmq.queues.created}")
    public void crear(NotificacionEvento evento, Channel channel,
                      @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        procesar(evento, channel, tag, "creación");
    }

    @RabbitListener(queues = "${app.rabbitmq.queues.status}")
    public void cambiarEstado(NotificacionEvento evento, Channel channel,
                              @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        procesar(evento, channel, tag, "estado");
    }

    @RabbitListener(queues = "${app.rabbitmq.queues.detail}")
    public void detalle(NotificacionEvento evento, Channel channel,
                        @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        procesar(evento, channel, tag, "detalle");
    }

    private void procesar(NotificacionEvento evento, Channel channel, long deliveryTag, String flujo) throws IOException {
        try {
            if (evento == null || evento.getEnvioId() == null) {
                channel.basicAck(deliveryTag, false);
                return;
            }
            if (notificacionService.existeEvento(evento.getEventId())) {
                log.info("Evento RabbitMQ duplicado {}; se confirma", evento.getEventId());
                channel.basicAck(deliveryTag, false);
                return;
            }

            CanalNotificacion canal = resolverCanal(evento.getCanal());
            String mensaje = evento.getMensaje() == null || evento.getMensaje().isBlank()
                    ? "Pedido " + evento.getEnvioId() + " - " + flujo : evento.getMensaje();
            Notificacion notificacion = new Notificacion(evento.getEnvioId(), evento.getDestinatario(), mensaje, canal);
            notificacion.setEventId(evento.getEventId());
            notificacion.setEnviada(true);
            notificacionService.guardar(notificacion);
            channel.basicAck(deliveryTag, false);
            log.info("Notificación {} procesada: envío {}, evento {}, canal {}", flujo, evento.getEnvioId(), evento.getEventId(), canal);
        } catch (Exception exception) {
            log.error("Error procesando flujo {}. El mensaje se enviará a su DLQ", flujo, exception);
            channel.basicNack(deliveryTag, false, false);
        }
    }

    private CanalNotificacion resolverCanal(String canal) {
        if (canal == null || canal.isBlank()) {
            return CanalNotificacion.EMAIL;
        }

        String valorNormalizado = canal.trim().toUpperCase();
        return switch (valorNormalizado) {
            case "EMAIL" -> CanalNotificacion.EMAIL;
            case "SMS" -> CanalNotificacion.SMS;
            case "PUSH", "PUSH_NOTIFICATION", "NOTIFICATION" -> CanalNotificacion.PUSH;
            default -> CanalNotificacion.EMAIL;
        };
    }
}
