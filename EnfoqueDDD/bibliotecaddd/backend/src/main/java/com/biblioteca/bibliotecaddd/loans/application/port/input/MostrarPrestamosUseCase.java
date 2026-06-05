
package com.biblioteca.bibliotecaddd.loans.application.port.input;

import com.biblioteca.bibliotecaddd.loans.application.dto.PrestamoResponse; 
import java.util.List;

public interface MostrarPrestamosUseCase {
    List<PrestamoResponse> listarTodos();          
    List<PrestamoResponse> listarPorUsuario(String usuarioId);
}