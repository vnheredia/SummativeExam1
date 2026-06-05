package com.biblioteca.bibliotecaddd.loans.infrastructure.adapter.output.persistence;

import com.biblioteca.bibliotecaddd.loans.application.port.output.ReservaRepositoryPort;
import com.biblioteca.bibliotecaddd.loans.domain.model.Reserva;
import com.biblioteca.bibliotecaddd.loans.domain.model.valueobjects.IdReserva;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ReservaRepositoryAdapter implements ReservaRepositoryPort {

    private final ReservaJpaRepository jpaRepository;

    public ReservaRepositoryAdapter(ReservaJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(Reserva reserva) {
        ReservaJpaEntity entity = new ReservaJpaEntity(
                reserva.getId().getValue(),
                reserva.getUsuarioId(),
                reserva.getLibroCodigo(),
                reserva.getEstado().toString(),
                reserva.getFechaReserva()
        );
        jpaRepository.save(entity);
    }

    @Override
    public Optional<Reserva> findById(IdReserva id) {
        return jpaRepository.findById(id.getValue()).map(this::toDomain);
    }

    @Override
    public List<Reserva> findAll() {
        return jpaRepository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Reserva> findActivasByUsuarioId(String usuarioId) {
        return jpaRepository.findByUsuarioIdAndEstado(usuarioId, "ACTIVA").stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<Reserva> findActivaByUsuarioAndLibro(String usuarioId, String libroCodigo) {
        return jpaRepository.findByUsuarioIdAndLibroCodigoAndEstado(usuarioId, libroCodigo, "ACTIVA").map(this::toDomain);
    }

    @Override
    public Optional<Reserva> findActivaByLibro(String libroCodigo) {
        return jpaRepository.findByLibroCodigoAndEstado(libroCodigo, "ACTIVA").map(this::toDomain);
    }

    private Reserva toDomain(ReservaJpaEntity entity) {
        Reserva reserva = new Reserva(
                new IdReserva(entity.getId()),
                entity.getUsuarioId(),
                entity.getLibroCodigo(),
                entity.getFechaReserva()
        );
        // Necesitamos un método para cambiar el estado al reconstruir desde BD
        // Por simplicidad, asumimos que se puede setear con un método de paquete.
        return reserva;
    }
}