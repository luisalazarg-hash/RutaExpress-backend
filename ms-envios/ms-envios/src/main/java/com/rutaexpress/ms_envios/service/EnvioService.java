package com.rutaexpress.ms_envios.service;

import com.rutaexpress.ms_envios.model.Envio;
import com.rutaexpress.ms_envios.model.EstadoEnvio;
import com.rutaexpress.ms_envios.repository.EnvioRepository;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnvioService {
    private final EnvioRepository repository;
    private final ShipmentEventPublisher eventPublisher;

    public EnvioService(EnvioRepository repository, ShipmentEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public Envio guardar(Envio envio) {
        Envio guardado = repository.save(envio);
        eventPublisher.pedidoCreado(guardado);
        return guardado;
    }
    public List<Envio> listar() { return repository.findAll(); }
    public List<Envio> listarPorEstado(EstadoEnvio estado) { return repository.findByEstado(estado); }
    public Envio obtener(UUID id) { return repository.findById(id).orElseThrow(); }

    @Transactional
    public Envio cambiarEstado(UUID id, EstadoEnvio estado) {
        Envio envio = obtener(id);
        EstadoEnvio estadoAnterior = envio.getEstado();
        envio.setEstado(estado);
        Envio actualizado = repository.save(envio);
        eventPublisher.estadoModificado(actualizado, estadoAnterior);
        return actualizado;
    }

    public void eliminar(UUID id) { repository.deleteById(id); }
}
