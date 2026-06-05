package com.biblioteca.bibliotecaddd.catalog.domain.service;

import com.biblioteca.bibliotecaddd.catalog.application.port.output.LibroRepositoryPort;
import com.biblioteca.bibliotecaddd.catalog.domain.model.valueobjects.CodigoLibro;
import org.springframework.stereotype.Service;

@Service
public class CatalogoDomainService {
    private final LibroRepositoryPort repository;

    public CatalogoDomainService(LibroRepositoryPort repository) {
        this.repository = repository;
    }

    public boolean isCodigoDisponible(CodigoLibro codigo) {
        return !repository.existsByCodigo(codigo);
    }
}