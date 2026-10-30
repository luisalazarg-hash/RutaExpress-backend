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

    @RabbitListener(queues = "${app.rabbitmq.queue}")
    public void recibir(NotificacionEvento evento, Channel channel,
                        @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        try {
            if (evento == null || evento.getEnvioId() == null) {
                log.warn("Evento de RabbitMQ recibido sin datos válidos; se confirma para evitar reintentos infinitos");
                channel.basicAck(deliveryTag, false);
                return;
            }

            CanalNotificacion canal = CanalNotificacion.valueOf(evento.getCanal().trim().toUpperCase());
            String mensaje = evento.getMensaje();
            if (mensaje == null || mensaje.isBlank()) {
                mensaje = "Pedido " + evento.getEnvioId();
                if ("shipment.status.changed".equals(evento.getEventType())) {
                    mensaje += " cambió de estado de " + evento.getEstadoAnterior() + " a " + evento.getEstadoNuevo();
                }
            }
            Notificacion notificacion = new Notificacion(evento.getEnvioId(), evento.getDestinatario(), mensaje, canal);
            notificacion.setEnviada(true);
            notificacionService.guardar(notificacion);
            channel.basicAck(deliveryTag, false);
            log.info("Notificación procesada para envío {} vía {}", evento.getEnvioId(), canal);
        } catch (Exception exception) {
            log.error("Error procesando notificación RabbitMQ; el mensaje será devuelto", exception);
            channel.basicNack(deliveryTag, false, true);
        }
    }
}
