package com.biblioteca.bibliotecaddd.loans.infrastructure.adapter.output;

import com.biblioteca.bibliotecaddd.catalog.application.port.output.ConsultarStockPort;
import com.biblioteca.bibliotecaddd.catalog.domain.repository.LibroRepository;
import com.biblioteca.bibliotecaddd.catalog.domain.model.valueobjects.CodigoLibro;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class ConsultarStockAdapter implements ConsultarStockPort {

    private final LibroRepository libroRepository;

    public ConsultarStockAdapter(LibroRepository libroRepository) {
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
}