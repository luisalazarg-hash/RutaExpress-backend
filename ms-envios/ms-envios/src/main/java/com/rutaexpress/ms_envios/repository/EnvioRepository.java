package com.rutaexpress.ms_envios.repository;

import com.rutaexpress.ms_envios.model.Envio;
import com.rutaexpress.ms_envios.model.EstadoEnvio;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnvioRepository extends JpaRepository<Envio, UUID> {
    List<Envio> findByEstado(EstadoEnvio estado);
}
