package com.biblioteca.bibliotecaddd.loans.domain.events;

import com.biblioteca.bibliotecaddd.loans.domain.model.valueobjects.MontoMulta;

public class LibroDevueltoEvent {
    private final String prestamoId;
    private final String usuarioId;
    private final String libroCodigo;
    private final MontoMulta multa; // puede ser null

    public LibroDevueltoEvent(String prestamoId, String usuarioId, String libroCodigo, MontoMulta multa) {
        this.prestamoId = prestamoId;
        this.usuarioId = usuarioId;
        this.libroCodigo = libroCodigo;
        this.multa = multa;
    }

    public String getPrestamoId() { return prestamoId; }
    public String getUsuarioId() { return usuarioId; }
    public String getLibroCodigo() { return libroCodigo; }
    public MontoMulta getMulta() { return multa; }
}