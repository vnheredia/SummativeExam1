package com.biblioteca.bibliotecaddd.loans.infrastructure.adapter.output;

import com.biblioteca.bibliotecaddd.catalog.application.port.output.ConsultarReservasPorLibroPort;
import com.biblioteca.bibliotecaddd.loans.infrastructure.adapter.output.persistence.ReservaJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class ConsultarReservasPorLibroAdapter implements ConsultarReservasPorLibroPort {

    private final ReservaJpaRepository reservaRepository;

    public ConsultarReservasPorLibroAdapter(ReservaJpaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    @Override
    public boolean existsActivaByLibroCodigo(String libroCodigo) {
        return reservaRepository.existsByLibroCodigoAndEstado(libroCodigo, "ACTIVA");
    }
}