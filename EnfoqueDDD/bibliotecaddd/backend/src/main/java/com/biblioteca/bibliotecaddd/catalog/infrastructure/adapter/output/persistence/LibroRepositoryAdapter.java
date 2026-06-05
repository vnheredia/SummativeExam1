package com.biblioteca.bibliotecaddd.catalog.infrastructure.adapter.output.persistence;

import com.biblioteca.bibliotecaddd.catalog.application.port.output.LibroRepositoryPort;
import com.biblioteca.bibliotecaddd.catalog.domain.model.Libro;
import com.biblioteca.bibliotecaddd.catalog.domain.model.valueobjects.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class LibroRepositoryAdapter implements LibroRepositoryPort {

    private final LibroJpaRepository jpaRepository;

    public LibroRepositoryAdapter(LibroJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(Libro libro) {
        LibroJpaEntity entity = new LibroJpaEntity(
                libro.getCodigo().getValue(),
                libro.getTitulo().getValue(),
                libro.getAutor().getValue(),
                libro.getStock().getValue()
        );
        jpaRepository.save(entity);
    }

    @Override
    public Optional<Libro> findByCodigo(CodigoLibro codigo) {
        return jpaRepository.findById(codigo.getValue())
                .map(entity -> new Libro(
                        new CodigoLibro(entity.getCodigo()),
                        new Titulo(entity.getTitulo()),
                        new Autor(entity.getAutor()),
                        new Stock(entity.getStock())
                ));
    }

    @Override
    public List<Libro> findAll() {
        return jpaRepository.findAll().stream()
                .map(entity -> new Libro(
                        new CodigoLibro(entity.getCodigo()),
                        new Titulo(entity.getTitulo()),
                        new Autor(entity.getAutor()),
                        new Stock(entity.getStock())
                ))
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByCodigo(CodigoLibro codigo) {
        return jpaRepository.existsById(codigo.getValue());
    }

    @Override
    public void delete(Libro libro) {
        jpaRepository.deleteById(libro.getCodigo().getValue());
    }
}