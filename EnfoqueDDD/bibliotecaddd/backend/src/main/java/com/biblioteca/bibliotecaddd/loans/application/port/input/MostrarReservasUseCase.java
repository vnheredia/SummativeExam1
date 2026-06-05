package com.biblioteca.bibliotecaddd.loans.application.port.input;

import com.biblioteca.bibliotecaddd.loans.application.dto.ReservaResponse;
import java.util.List;

public interface MostrarReservasUseCase {
    List<ReservaResponse> listarTodasReservas();
    List<ReservaResponse> listarReservasPorUsuario(String usuarioId);
}