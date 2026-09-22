package com.rutaexpress.ms_auth.service;

import com.rutaexpress.ms_auth.model.Empresa;
import com.rutaexpress.ms_auth.repository.EmpresaRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    @Transactional(readOnly = true)
    public List<Empresa> listarEmpresas() {
        return empresaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Empresa obtenerEmpresaPorId(Long id) {

        return empresaRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "No existe una empresa con ID: " + id
                        )
                );
    }

    @Transactional(readOnly = true)
    public Empresa obtenerEmpresaActiva(Long id) {

        Empresa empresa = obtenerEmpresaPorId(id);

        if (!Boolean.TRUE.equals(empresa.getActiva())) {
            throw new IllegalStateException(
                    "La empresa se encuentra desactivada"
            );
        }

        return empresa;
    }
}