package com.biblioteca.bibliotecaddd.loans.infrastructure.adapter.output.persistence;

import com.biblioteca.bibliotecaddd.loans.application.port.output.PrestamoRepositoryPort;
import com.biblioteca.bibliotecaddd.loans.domain.model.Prestamo;
import com.biblioteca.bibliotecaddd.loans.domain.model.valueobjects.IdPrestamo;
import com.biblioteca.bibliotecaddd.loans.domain.model.valueobjects.PeriodoPrestamo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PrestamoRepositoryAdapter implements PrestamoRepositoryPort {

    private final PrestamoJpaRepository jpaRepository;

    public PrestamoRepositoryAdapter(PrestamoJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(Prestamo prestamo) {
        PrestamoJpaEntity entity = new PrestamoJpaEntity(
                prestamo.getId().getValue(),
                prestamo.getUsuarioId(),
                prestamo.getLibroCodigo(),
                prestamo.getEstado().toString(),
                prestamo.getPeriodo().getFechaInicio(),
                prestamo.getPeriodo().getFechaLimite(),
                prestamo.getMultaGenerada() != null ? prestamo.getMultaGenerada().value() : 0.0
        );
        jpaRepository.save(entity);
    }

    @Override
    public Optional<Prestamo> findById(IdPrestamo id) {
        return jpaRepository.findById(id.getValue())
                .map(this::toDomain);
    }

    @Override
    public List<Prestamo> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Prestamo> findByUsuarioId(String usuarioId) {
        return jpaRepository.findByUsuarioId(usuarioId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Prestamo> findActivosByUsuarioId(String usuarioId) {
        return jpaRepository.findByUsuarioIdAndEstado(usuarioId, "ACTIVO").stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsActivoByUsuarioAndLibro(String usuarioId, String libroCodigo) {
        return jpaRepository.existsActivoByUsuarioAndLibro(usuarioId, libroCodigo);
    }

    private Prestamo toDomain(PrestamoJpaEntity entity) {
        PeriodoPrestamo periodo = new PeriodoPrestamo(entity.getFechaInicio(), 14);
        Prestamo prestamo = new Prestamo(
                new IdPrestamo(entity.getId()),
                entity.getUsuarioId(),
                entity.getLibroCodigo(),
                periodo
        );
        
        return prestamo;
    }
}