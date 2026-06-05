package com.biblioteca.bibliotecaddd.loans.infrastructure.adapter.output;

import com.biblioteca.bibliotecaddd.catalog.application.port.output.ActualizarStockPort;
import com.biblioteca.bibliotecaddd.catalog.domain.model.Libro;
import com.biblioteca.bibliotecaddd.catalog.domain.model.valueobjects.CodigoLibro;
import com.biblioteca.bibliotecaddd.catalog.domain.repository.LibroRepository;
import org.springframework.stereotype.Component;

@Component
public class ActualizarStockAdapter implements ActualizarStockPort {

    private final LibroRepository libroRepository;

    public ActualizarStockAdapter(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    @Override
    public void decrementarStock(String codigo) {
        Libro libro = libroRepository.findByCodigo(new CodigoLibro(codigo))
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado"));
        libro.prestarEjemplar();
        libroRepository.save(libro);
    }

    @Override
    public void incrementarStock(String codigo) {
        Libro libro = libroRepository.findByCodigo(new CodigoLibro(codigo))
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado"));
        libro.devolverEjemplar();
        libroRepository.save(libro);
    }
}