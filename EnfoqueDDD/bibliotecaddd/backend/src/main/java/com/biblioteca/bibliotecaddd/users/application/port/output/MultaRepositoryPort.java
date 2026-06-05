package com.biblioteca.bibliotecaddd.users.application.port.output;

import com.biblioteca.bibliotecaddd.users.domain.model.Multa;
import com.biblioteca.bibliotecaddd.users.domain.model.valueobjects.UserId;
import java.util.Optional;

public interface MultaRepositoryPort {
    void save(Multa multa);
    Optional<Multa> findByUsuarioId(UserId id);
    boolean existsByUsuarioId(UserId id);
}