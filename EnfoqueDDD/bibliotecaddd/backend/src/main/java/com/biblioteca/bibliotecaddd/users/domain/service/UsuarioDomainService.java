package com.biblioteca.bibliotecaddd.users.domain.service;

import com.biblioteca.bibliotecaddd.users.application.port.output.UsuarioRepositoryPort;
import com.biblioteca.bibliotecaddd.users.domain.model.valueobjects.UserId;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDomainService {
    private final UsuarioRepositoryPort repository;

    public UsuarioDomainService(UsuarioRepositoryPort repository) {
        this.repository = repository;
    }

    public boolean isIdDisponible(UserId id) {
        return !repository.existsById(id);
    }
}