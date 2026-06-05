package com.biblioteca.bibliotecaddd.users.application.port.input;

import com.biblioteca.bibliotecaddd.users.application.dto.RegistrarUsuarioRequest;
import com.biblioteca.bibliotecaddd.users.application.dto.UsuarioResponse;

public interface RegistrarUsuarioUseCase {
    UsuarioResponse registrar(RegistrarUsuarioRequest request);
}