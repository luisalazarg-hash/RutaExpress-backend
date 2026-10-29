package com.rutaexpress.ms_notificaciones.service;

import com.rutaexpress.ms_notificaciones.dto.NotificacionEvento;
import com.rutaexpress.ms_notificaciones.model.CanalNotificacion;
import com.rutaexpress.ms_notificaciones.model.Notificacion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class NotificacionConsumer {
    private static final Logger log = LoggerFactory.getLogger(NotificacionConsumer.class);

    private final NotificacionService notificacionService;

    public NotificacionConsumer(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @RabbitListener(queues = "${app.rabbitmq.queue}")
    public void recibir(NotificacionEvento evento) {
        if (evento == null || evento.getEnvioId() == null) {
            log.warn("Evento de RabbitMQ recibido sin datos válidos");
            return;
        }

        CanalNotificacion canal = CanalNotificacion.valueOf(evento.getCanal().trim().toUpperCase());
        Notificacion notificacion = new Notificacion(evento.getEnvioId(), evento.getDestinatario(), evento.getMensaje(), canal);
        notificacion.setEnviada(true);
        notificacionService.guardar(notificacion);

        log.info("Notificación procesada para envío {} vía {}", evento.getEnvioId(), canal);
    }
}
