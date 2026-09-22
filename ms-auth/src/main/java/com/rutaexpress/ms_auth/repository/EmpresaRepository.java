package com.rutaexpress.ms_auth.repository;

import com.rutaexpress.ms_auth.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpresaRepository
        extends JpaRepository<Empresa, Long> {

    Optional<Empresa> findByRut(String rut);
}