package com.biblioteca.bibliotecaddd.users.domain.repository;

import com.biblioteca.bibliotecaddd.users.domain.model.Multa;
import com.biblioteca.bibliotecaddd.users.domain.model.valueobjects.UserId;
import java.util.Optional;

public interface MultaRepository {
    void save(Multa multa);
    Optional<Multa> findByUsuarioId(UserId id);
    boolean existsByUsuarioId(UserId id);
}