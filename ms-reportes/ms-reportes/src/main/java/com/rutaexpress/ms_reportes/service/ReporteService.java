package com.rutaexpress.ms_reportes.service;

import com.rutaexpress.ms_reportes.model.Reporte;
import com.rutaexpress.ms_reportes.repository.ReporteRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ReporteService {
    private final ReporteRepository repository;

    public ReporteService(ReporteRepository repository) {
        this.repository = repository;
    }

    public Reporte guardar(Reporte reporte) { return repository.save(reporte); }
    public List<Reporte> listar() { return repository.findAll(); }
    public Reporte obtener(UUID id) { return repository.findById(id).orElseThrow(); }
}
