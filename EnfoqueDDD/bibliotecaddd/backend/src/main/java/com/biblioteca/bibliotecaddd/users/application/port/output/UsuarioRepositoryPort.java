package com.biblioteca.bibliotecaddd.users.application.port.output;

import com.biblioteca.bibliotecaddd.users.domain.model.Usuario;
import com.biblioteca.bibliotecaddd.users.domain.model.valueobjects.UserId;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepositoryPort {
    void save(Usuario usuario);
    Optional<Usuario> findById(UserId id);
    boolean existsById(UserId id);
    // otros métodos necesarios
    void delete(Usuario usuario);
    List<Usuario> findAll();
}