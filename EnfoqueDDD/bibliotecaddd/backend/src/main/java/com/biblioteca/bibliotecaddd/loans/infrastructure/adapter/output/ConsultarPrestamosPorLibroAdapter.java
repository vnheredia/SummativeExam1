package com.biblioteca.bibliotecaddd.loans.infrastructure.adapter.output;

import com.biblioteca.bibliotecaddd.catalog.application.port.output.ConsultarPrestamosPorLibroPort;
import com.biblioteca.bibliotecaddd.loans.infrastructure.adapter.output.persistence.PrestamoJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class ConsultarPrestamosPorLibroAdapter implements ConsultarPrestamosPorLibroPort {

    private final PrestamoJpaRepository prestamoRepository;

    public ConsultarPrestamosPorLibroAdapter(PrestamoJpaRepository prestamoRepository) {
        this.prestamoRepository = prestamoRepository;
    }

    @Override
    public boolean existsActivoByLibroCodigo(String libroCodigo) {
        return prestamoRepository.existsByLibroCodigoAndEstado(libroCodigo, "ACTIVO");
    }
}