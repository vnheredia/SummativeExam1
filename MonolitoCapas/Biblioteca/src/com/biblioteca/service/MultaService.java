package MonolitoCapas.Biblioteca.src.com.biblioteca.service;


import MonolitoCapas.Biblioteca.src.com.biblioteca.repository.MultaRepository;
import java.util.UUID;

public class MultaService {
    private final MultaRepository multaRepository = MultaRepository.getInstance();

    public boolean tieneMultasPendientes(UUID idUsuario) {
        return multaRepository.tieneMultasPendientes(idUsuario);
    }

    public void agregarMulta(UUID idUsuario, double monto) {
        multaRepository.agregarMulta(idUsuario, monto);
    }

    public double obtenerMulta(UUID idUsuario) {
        return multaRepository.obtenerMonto(idUsuario);
    }

    public boolean pagarMulta(UUID idUsuario) {
        if (!tieneMultasPendientes(idUsuario)) return false;
        multaRepository.pagarMulta(idUsuario);
        return true;
    }
}
