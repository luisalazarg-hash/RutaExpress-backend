package com.rutaexpress.ms_catalogo.service;

import com.rutaexpress.ms_catalogo.model.Producto;
import com.rutaexpress.ms_catalogo.repository.ProductoRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {
    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    public Producto guardar(Producto producto) { return repository.save(producto); }
    public List<Producto> listar() { return repository.findByActivoTrue(); }
    public Producto obtener(UUID id) { return repository.findById(id).orElseThrow(); }
    public void eliminar(UUID id) { repository.deleteById(id); }
}
