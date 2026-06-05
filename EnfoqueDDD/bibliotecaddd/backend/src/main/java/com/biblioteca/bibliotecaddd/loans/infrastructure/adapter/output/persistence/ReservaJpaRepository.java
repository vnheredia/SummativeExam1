package com.biblioteca.bibliotecaddd.loans.infrastructure.adapter.output.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ReservaJpaRepository extends JpaRepository<ReservaJpaEntity, String> {
    List<ReservaJpaEntity> findByUsuarioIdAndEstado(String usuarioId, String estado);
    Optional<ReservaJpaEntity> findByUsuarioIdAndLibroCodigoAndEstado(String usuarioId, String libroCodigo, String estado);
    Optional<ReservaJpaEntity> findByLibroCodigoAndEstado(String libroCodigo, String estado);
}