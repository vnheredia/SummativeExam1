package com.biblioteca.bibliotecaddd.users.domain.service;

import com.biblioteca.bibliotecaddd.users.domain.repository.UsuarioRepository;
import com.biblioteca.bibliotecaddd.users.domain.model.valueobjects.UserId;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDomainService {
    private final UsuarioRepository repository;

    public UsuarioDomainService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public boolean isIdDisponible(UserId id) {
        return !repository.existsById(id);
    }
}