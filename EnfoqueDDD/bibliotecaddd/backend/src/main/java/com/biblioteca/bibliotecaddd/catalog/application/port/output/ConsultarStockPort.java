package com.biblioteca.bibliotecaddd.catalog.application.port.output;

import java.util.Optional;

public interface ConsultarStockPort {
    Optional<Integer> getStockByCodigo(String codigo);
    boolean existsByCodigo(String codigo);
}