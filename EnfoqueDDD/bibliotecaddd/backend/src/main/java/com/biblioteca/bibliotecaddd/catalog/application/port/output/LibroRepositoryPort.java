package com.biblioteca.bibliotecaddd.catalog.application.port.output;

import com.biblioteca.bibliotecaddd.catalog.domain.model.Libro;
import com.biblioteca.bibliotecaddd.catalog.domain.model.valueobjects.CodigoLibro;
import java.util.List;
import java.util.Optional;

public interface LibroRepositoryPort {
    void save(Libro libro);
    Optional<Libro> findByCodigo(CodigoLibro codigo);
    List<Libro> findAll();
    boolean existsByCodigo(CodigoLibro codigo);
    void delete(Libro libro);
}