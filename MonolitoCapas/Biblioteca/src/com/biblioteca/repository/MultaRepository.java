package MonolitoCapas.Biblioteca.src.com.biblioteca.repository;

import MonolitoCapas.Biblioteca.src.com.biblioteca.model.Multa;
import java.util.*;

public class MultaRepository {
    private static MultaRepository instance;
    private final Map<UUID, Multa> multas = new HashMap<>();

    private MultaRepository() {}

    public static MultaRepository getInstance() {
        if (instance == null) instance = new MultaRepository();
        return instance;
    }

    public void save(Multa multa) {
        multas.put(multa.getIdUsuario(), multa);
    }

    public Optional<Multa> findByUsuario(UUID idUsuario) {
        return Optional.ofNullable(multas.get(idUsuario));
    }

    public boolean tieneMultasPendientes(UUID idUsuario) {
        Multa m = multas.get(idUsuario);
        return m != null && m.getMonto() > 0;
    }

    public void agregarMulta(UUID idUsuario, double monto) {
        Multa existente = multas.get(idUsuario);
        if (existente != null) {
            existente.setMonto(existente.getMonto() + monto);
        } else {
            multas.put(idUsuario, new Multa(idUsuario, monto));
        }
    }

    public void pagarMulta(UUID idUsuario) {
        Multa m = multas.get(idUsuario);
        if (m != null) {
            m.setMonto(0.0);
        }
    }

    public double obtenerMonto(UUID idUsuario) {
        Multa m = multas.get(idUsuario);
        return m == null ? 0.0 : m.getMonto();
    }
}