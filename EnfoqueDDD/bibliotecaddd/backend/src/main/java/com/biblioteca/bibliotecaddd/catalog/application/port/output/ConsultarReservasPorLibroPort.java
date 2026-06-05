package com.biblioteca.bibliotecaddd.catalog.application.port.output;

public interface ConsultarReservasPorLibroPort {
    boolean existsActivaByLibroCodigo(String libroCodigo);
}