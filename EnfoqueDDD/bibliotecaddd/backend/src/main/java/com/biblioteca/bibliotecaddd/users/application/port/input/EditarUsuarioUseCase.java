package com.biblioteca.bibliotecaddd.users.application.port.input;

import com.biblioteca.bibliotecaddd.users.application.dto.EditarUsuarioRequest;
import com.biblioteca.bibliotecaddd.users.application.dto.UsuarioResponse;

public interface EditarUsuarioUseCase {
    UsuarioResponse editar(String id, EditarUsuarioRequest request);
}