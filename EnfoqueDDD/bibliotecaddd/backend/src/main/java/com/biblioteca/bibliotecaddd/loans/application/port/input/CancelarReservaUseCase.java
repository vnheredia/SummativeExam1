package com.biblioteca.bibliotecaddd.loans.application.port.input;

import com.biblioteca.bibliotecaddd.loans.application.dto.CancelarReservaRequest;

public interface CancelarReservaUseCase {
    void cancelar(CancelarReservaRequest request);
}