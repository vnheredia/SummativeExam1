package com.biblioteca.bibliotecaddd.loans.domain.repository;

import com.biblioteca.bibliotecaddd.loans.domain.model.Prestamo;
import com.biblioteca.bibliotecaddd.loans.domain.model.valueobjects.IdPrestamo;
import java.util.List;
import java.util.Optional;

public interface PrestamoRepository {
    void save(Prestamo prestamo);
    Optional<Prestamo> findById(IdPrestamo id);
    List<Prestamo> findAll();
    List<Prestamo> findByUsuarioId(String usuarioId);
    List<Prestamo> findActivosByUsuarioId(String usuarioId);
    boolean existsActivoByUsuarioAndLibro(String usuarioId, String libroCodigo);
}