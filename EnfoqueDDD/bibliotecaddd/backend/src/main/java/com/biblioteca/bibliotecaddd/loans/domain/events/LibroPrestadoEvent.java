package com.biblioteca.bibliotecaddd.loans.domain.events;

import lombok.Getter;

@Getter
public class LibroPrestadoEvent {
    private final String prestamoId;
    private final String usuarioId;
    private final String libroCodigo;

    public LibroPrestadoEvent(String prestamoId, String usuarioId, String libroCodigo) {
        this.prestamoId = prestamoId;
        this.usuarioId = usuarioId;
        this.libroCodigo = libroCodigo;
    }
}