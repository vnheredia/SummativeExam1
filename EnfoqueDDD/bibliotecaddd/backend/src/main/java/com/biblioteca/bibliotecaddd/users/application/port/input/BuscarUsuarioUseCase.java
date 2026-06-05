package com.biblioteca.bibliotecaddd.users.application.port.input;

import com.biblioteca.bibliotecaddd.users.application.dto.UsuarioResponse;
import java.util.List;
import java.util.Optional;

public interface BuscarUsuarioUseCase {
    Optional<UsuarioResponse> buscarPorId(String id);
    List<UsuarioResponse> listarTodos();
}