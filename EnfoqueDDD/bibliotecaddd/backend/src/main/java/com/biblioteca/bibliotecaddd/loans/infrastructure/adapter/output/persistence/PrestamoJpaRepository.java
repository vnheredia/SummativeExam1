package com.biblioteca.bibliotecaddd.loans.infrastructure.adapter.output.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrestamoJpaRepository extends JpaRepository<PrestamoJpaEntity, String> {
    List<PrestamoJpaEntity> findByUsuarioId(String usuarioId);
    List<PrestamoJpaEntity> findByUsuarioIdAndEstado(String usuarioId, String estado);
    @Query("SELECT COUNT(p) > 0 FROM PrestamoJpaEntity p WHERE p.usuarioId = :usuarioId AND p.libroCodigo = :libroCodigo AND p.estado = 'ACTIVO'")
    boolean existsActivoByUsuarioAndLibro(@Param("usuarioId") String usuarioId, @Param("libroCodigo") String libroCodigo);
}