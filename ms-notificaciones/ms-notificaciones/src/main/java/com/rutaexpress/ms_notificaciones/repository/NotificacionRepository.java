package com.rutaexpress.ms_notificaciones.repository;

import com.rutaexpress.ms_notificaciones.model.Notificacion;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacionRepository extends JpaRepository<Notificacion, UUID> {
    List<Notificacion> findByEnvioId(UUID envioId);
}
