package com.rutaexpress.ms_reportes.repository;

import com.rutaexpress.ms_reportes.model.Reporte;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReporteRepository extends JpaRepository<Reporte, UUID> {
}
