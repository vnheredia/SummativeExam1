package com.biblioteca.bibliotecaddd.catalog.application.port.output;

public interface ActualizarStockPort {
    void decrementarStock(String codigo);
    void incrementarStock(String codigo);
}