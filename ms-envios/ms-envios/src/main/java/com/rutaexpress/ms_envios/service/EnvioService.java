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

    public EnvioService(EnvioRepository repository) {
        this.repository = repository;
    }

    public Envio guardar(Envio envio) { return repository.save(envio); }
    public List<Envio> listar() { return repository.findAll(); }
    public List<Envio> listarPorEstado(EstadoEnvio estado) { return repository.findByEstado(estado); }
    public Envio obtener(UUID id) { return repository.findById(id).orElseThrow(); }

    @Transactional
    public Envio cambiarEstado(UUID id, EstadoEnvio estado) {
        Envio envio = obtener(id);
        envio.setEstado(estado);
        return repository.save(envio);
    }
}
