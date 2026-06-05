package com.biblioteca.bibliotecaddd.users.application.port.input;

public interface GestionarMultasUseCase {
    boolean tieneMultasPendientes(String userId);
    void registrarMulta(String userId, double monto);
    double pagarMultas(String userId);
}