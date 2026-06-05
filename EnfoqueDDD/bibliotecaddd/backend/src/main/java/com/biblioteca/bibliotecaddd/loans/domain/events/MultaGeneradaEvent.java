package com.biblioteca.bibliotecaddd.loans.domain.events;

import com.biblioteca.bibliotecaddd.loans.domain.model.valueobjects.MontoMulta;

public record MultaGeneradaEvent(String usuarioId, MontoMulta monto) {
}