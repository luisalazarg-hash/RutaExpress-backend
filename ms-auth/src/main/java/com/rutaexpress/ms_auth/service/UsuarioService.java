package com.rutaexpress.ms_auth.service;

import com.rutaexpress.ms_auth.dto.CrearUsuarioRequest;
import com.rutaexpress.ms_auth.dto.UsuarioResponse;

import com.rutaexpress.ms_auth.model.Empresa;
import com.rutaexpress.ms_auth.model.EstadoUsuario;
import com.rutaexpress.ms_auth.model.Rol;
import com.rutaexpress.ms_auth.model.Usuario;
import com.rutaexpress.ms_auth.exception.AccesoDenegadoException;

import com.rutaexpress.ms_auth.repository.UsuarioRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EmpresaService empresaService;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            EmpresaService empresaService
    ) {
        this.usuarioRepository = usuarioRepository;
        this.empresaService = empresaService;
    }

    @Transactional(readOnly = true)
        public Usuario obtenerUsuarioActivo(
                UUID entraOid,
                UUID entraTid
        ) {

        Usuario usuario = usuarioRepository
                .findByEntraOidAndEntraTid(entraOid, entraTid)
                .orElseThrow(() ->
                        new AccesoDenegadoException(
                                "El usuario autenticado no está registrado en RutaExpress"
                        )
                );

        if (usuario.getEstado() != EstadoUsuario.ACTIVO) {
                throw new AccesoDenegadoException(
                        "El usuario no se encuentra activo"
                );
        }

        return usuario;
        }

    @Transactional
    public UsuarioResponse crearUsuario(CrearUsuarioRequest request) {

        if (request == null) {
            throw new IllegalArgumentException(
                    "Los datos del usuario son obligatorios"
            );
        }

        String nombre = request.nombre() == null
                ? ""
                : request.nombre().trim();

        String email = request.email() == null
                ? ""
                : request.email().trim().toLowerCase(Locale.ROOT);

        if (nombre.isBlank() || nombre.length() > 150) {
            throw new IllegalArgumentException(
                    "El nombre es obligatorio y no puede superar 150 caracteres"
            );
        }

        if (email.isBlank() || email.length() > 255) {
            throw new IllegalArgumentException(
                    "El correo es obligatorio y no puede superar 255 caracteres"
            );
        }

        if (request.rol() == null) {
            throw new IllegalArgumentException(
                    "El rol es obligatorio"
            );
        }

        if (usuarioRepository.existsByEmail(email)) {
            throw new IllegalArgumentException(
                    "Ya existe un usuario registrado con ese correo"
            );
        }

        Empresa empresa = null;

        if (request.rol() != Rol.ADMIN) {

            if (request.empresaId() == null) {
                throw new IllegalArgumentException(
                        "Debe seleccionar una empresa para este rol"
                );
            }

            empresa = empresaService.obtenerEmpresaActiva(
                    request.empresaId()
            );

        } else if (request.empresaId() != null) {

            throw new IllegalArgumentException(
                    "El administrador de plataforma no debe tener una empresa asociada"
            );
        }

        Usuario usuario = new Usuario();

        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setRol(request.rol());
        usuario.setEstado(EstadoUsuario.PENDIENTE);
        usuario.setEmpresa(empresa);

        Usuario guardado = usuarioRepository.save(usuario);

        return convertirAResponse(guardado);
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponse> listarUsuarios() {

        return usuarioRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public UsuarioResponse obtenerUsuarioPorId(Long id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "No existe un usuario con ID: " + id
                        )
                );

        return convertirAResponse(usuario);
    }

    @Transactional(readOnly = true)
	public UsuarioResponse obtenerUsuarioActual(UUID entraOid, UUID entraTid){
		Usuario usuario = obtenerUsuarioActivo(entraOid, entraTid);

		return convertirAResponse(usuario);
	}

    private UsuarioResponse convertirAResponse(Usuario usuario) {

        Empresa empresa = usuario.getEmpresa();

        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getRol(),
                usuario.getEstado(),
                empresa != null ? empresa.getId() : null,
                empresa != null ? empresa.getNombre() : null,
                usuario.getFechaCreacion()
        );
    }
}