package com.biblioteca.bibliotecaddd.users.application.service;

import com.biblioteca.bibliotecaddd.users.application.dto.EditarUsuarioRequest;
import com.biblioteca.bibliotecaddd.users.application.dto.RegistrarUsuarioRequest;
import com.biblioteca.bibliotecaddd.users.application.dto.UsuarioResponse;
import com.biblioteca.bibliotecaddd.users.application.port.input.*;
import com.biblioteca.bibliotecaddd.users.application.port.output.UsuarioRepositoryPort;
import com.biblioteca.bibliotecaddd.users.domain.model.Usuario;
import com.biblioteca.bibliotecaddd.users.domain.model.valueobjects.NombreUsuario;
import com.biblioteca.bibliotecaddd.users.domain.model.valueobjects.UserId;
import com.biblioteca.bibliotecaddd.users.domain.service.UsuarioDomainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UsuarioApplicationService implements
        RegistrarUsuarioUseCase,
        BuscarUsuarioUseCase,
        EditarUsuarioUseCase,
        EliminarUsuarioUseCase {

    private final UsuarioRepositoryPort repository;
    private final UsuarioDomainService domainService;

    public UsuarioApplicationService(UsuarioRepositoryPort repository, UsuarioDomainService domainService) {
        this.repository = repository;
        this.domainService = domainService;
    }

    @Override
    public UsuarioResponse registrar(RegistrarUsuarioRequest request) {
        UserId id = new UserId(request.getId());
        if (!domainService.isIdDisponible(id)) {
            throw new IllegalArgumentException("El ID de usuario ya existe");
        }
        NombreUsuario nombre = new NombreUsuario(request.getNombre());
        Usuario usuario = new Usuario(id, nombre);
        repository.save(usuario);
        return toResponse(usuario);
    }

    @Override
    public Optional<UsuarioResponse> buscarPorId(String id) {
        return repository.findById(new UserId(id))
                .map(this::toResponse);
    }

    @Override
    public List<UsuarioResponse> listarTodos() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioResponse editar(String id, EditarUsuarioRequest request) {
        UserId userId = new UserId(id);
        Usuario usuario = repository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        usuario.cambiarNombre(new NombreUsuario(request.getNombre()));
        repository.save(usuario);
        return toResponse(usuario);
    }

    @Override
    public void eliminar(String id) {
        UserId userId = new UserId(id);
        Usuario usuario = repository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        repository.delete(usuario);
    }

    private UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(usuario.getId().getValue(), usuario.getNombre().getNombre());
    }
}