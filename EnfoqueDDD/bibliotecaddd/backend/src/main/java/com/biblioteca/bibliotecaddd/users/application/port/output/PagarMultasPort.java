package com.biblioteca.bibliotecaddd.users.application.port.output;

public interface PagarMultasPort {
    double pagarMultas(String userId); // devuelve el monto pagado (el total pendiente)
}