package com.biblioteca.bibliotecaddd.catalog.application.port.output;

public interface ConsultarPrestamosPorLibroPort {
    boolean existsActivoByLibroCodigo(String libroCodigo);
}