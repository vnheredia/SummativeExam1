package com.biblioteca.bibliotecaddd.catalog.infrastructure.adapter.output;

import com.biblioteca.bibliotecaddd.catalog.application.port.output.ActualizarStockPort;
import com.biblioteca.bibliotecaddd.catalog.application.port.output.ConsultarStockPort;
import com.biblioteca.bibliotecaddd.catalog.application.port.output.LibroRepositoryPort;
import com.biblioteca.bibliotecaddd.catalog.domain.model.Libro;
import com.biblioteca.bibliotecaddd.catalog.domain.model.valueobjects.CodigoLibro;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class StockAdapter implements ConsultarStockPort, ActualizarStockPort {

    private final LibroRepositoryPort libroRepository; // ← cambiado a LibroRepositoryPort

    public StockAdapter(LibroRepositoryPort libroRepository) { // ← constructor actualizado
        this.libroRepository = libroRepository;
    }

    @Override
    public Optional<Integer> getStockByCodigo(String codigo) {
        return libroRepository.findByCodigo(new CodigoLibro(codigo))
                .map(libro -> libro.getStock().getValue());
    }

    @Override
    public boolean existsByCodigo(String codigo) {
        return libroRepository.existsByCodigo(new CodigoLibro(codigo));
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