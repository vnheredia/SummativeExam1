package com.biblioteca.bibliotecaddd.loans.application.port.output;

import com.biblioteca.bibliotecaddd.loans.domain.model.Reserva;
import com.biblioteca.bibliotecaddd.loans.domain.model.valueobjects.IdReserva;
import java.util.List;
import java.util.Optional;

public interface ReservaRepositoryPort {
    void save(Reserva reserva);
    Optional<Reserva> findById(IdReserva id);
    List<Reserva> findAll();
    List<Reserva> findActivasByUsuarioId(String usuarioId);
    Optional<Reserva> findActivaByUsuarioAndLibro(String usuarioId, String libroCodigo);
    Optional<Reserva> findActivaByLibro(String libroCodigo);
}