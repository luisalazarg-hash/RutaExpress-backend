package com.rutaexpress.ms_notificaciones.service;

import com.rutaexpress.ms_notificaciones.model.Notificacion;
import com.rutaexpress.ms_notificaciones.repository.NotificacionRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class NotificacionService {
    private final NotificacionRepository repository;

    public NotificacionService(NotificacionRepository repository) {
        this.repository = repository;
    }

    public Notificacion guardar(Notificacion notificacion) { return repository.save(notificacion); }
    public List<Notificacion> listarPorEnvio(UUID envioId) { return repository.findByEnvioId(envioId); }
    public Notificacion obtener(UUID id) { return repository.findById(id).orElseThrow(); }
}
