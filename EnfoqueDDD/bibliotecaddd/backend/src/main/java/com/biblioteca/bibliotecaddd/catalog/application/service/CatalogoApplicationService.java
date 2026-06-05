package com.biblioteca.bibliotecaddd.catalog.application.service;

import com.biblioteca.bibliotecaddd.catalog.application.dto.EditarLibroRequest;
import com.biblioteca.bibliotecaddd.catalog.application.dto.LibroResponse;
import com.biblioteca.bibliotecaddd.catalog.application.dto.RegistrarLibroRequest;
import com.biblioteca.bibliotecaddd.catalog.application.port.input.*;
import com.biblioteca.bibliotecaddd.catalog.application.port.output.LibroRepositoryPort;
import com.biblioteca.bibliotecaddd.catalog.domain.model.Libro;
import com.biblioteca.bibliotecaddd.catalog.domain.model.valueobjects.*;
import com.biblioteca.bibliotecaddd.catalog.domain.service.CatalogoDomainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CatalogoApplicationService implements
        RegistrarLibroUseCase,
        BuscarLibroUseCase,
        EditarLibroUseCase,
        EliminarLibroUseCase {

    private final LibroRepositoryPort repository;
    private final CatalogoDomainService domainService;

    public CatalogoApplicationService(LibroRepositoryPort repository, CatalogoDomainService domainService) {
        this.repository = repository;
        this.domainService = domainService;
    }

    @Override
    public LibroResponse registrar(RegistrarLibroRequest request) {
        CodigoLibro codigo = new CodigoLibro(request.getCodigo());
        if (!domainService.isCodigoDisponible(codigo)) {
            throw new IllegalArgumentException("Ya existe un libro con ese código");
        }
        Titulo titulo = new Titulo(request.getTitulo());
        Autor autor = new Autor(request.getAutor());
        Stock stock = new Stock(request.getStock());

        Libro libro = new Libro(codigo, titulo, autor, stock);
        repository.save(libro);
        return toResponse(libro);
    }

    @Override
    public Optional<LibroResponse> buscarPorCodigo(String codigo) {
        return repository.findByCodigo(new CodigoLibro(codigo))
                .map(this::toResponse);
    }

    @Override
    public List<LibroResponse> listarTodos() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public LibroResponse editar(String codigo, EditarLibroRequest request) {
        CodigoLibro cod = new CodigoLibro(codigo);
        Libro libro = repository.findByCodigo(cod)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado"));
        libro.actualizarDatos(new Titulo(request.getTitulo()), new Autor(request.getAutor()));
        libro.actualizarStock(new Stock(request.getStock()));
        repository.save(libro);
        return toResponse(libro);
    }

    @Override
    public void eliminar(String codigo) {
        CodigoLibro cod = new CodigoLibro(codigo);
        Libro libro = repository.findByCodigo(cod)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado"));
        repository.delete(libro);
    }

    private LibroResponse toResponse(Libro libro) {
        return new LibroResponse(
                libro.getCodigo().getValue(),
                libro.getTitulo().getValue(),
                libro.getAutor().getValue(),
                libro.getStock().getValue()
        );
    }
}