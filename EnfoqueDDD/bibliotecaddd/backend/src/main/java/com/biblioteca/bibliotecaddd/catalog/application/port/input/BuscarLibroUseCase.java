package com.biblioteca.bibliotecaddd.catalog.application.port.input;

import com.biblioteca.bibliotecaddd.catalog.application.dto.LibroResponse;
import java.util.List;
import java.util.Optional;

public interface BuscarLibroUseCase {
    Optional<LibroResponse> buscarPorCodigo(String codigo);
    List<LibroResponse> listarTodos();
}