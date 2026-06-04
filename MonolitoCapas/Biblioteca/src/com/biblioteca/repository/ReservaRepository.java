package MonolitoCapas.Biblioteca.src.com.biblioteca.repository;

import MonolitoCapas.Biblioteca.src.com.biblioteca.model.Reserva;
import java.util.*;

public class ReservaRepository {
    private static ReservaRepository instance;
    private final List<Reserva> reservas = new ArrayList<>();

    private ReservaRepository() {}

    public static ReservaRepository getInstance() {
        if (instance == null) instance = new ReservaRepository();
        return instance;
    }

    public void save(Reserva reserva) {
        reservas.add(reserva);
    }

    public List<Reserva> findAll() {
        return new ArrayList<>(reservas);
    }

    public Optional<Reserva> findById(UUID id) {
        return reservas.stream().filter(r -> r.getId().equals(id)).findFirst();
    }

    public List<Reserva> findActivasByLibro(UUID idLibro) {
        return reservas.stream()
                .filter(r -> r.getIdLibro().equals(idLibro) && r.getEstado().equals("ACTIVA"))
                .toList();
    }

    public boolean existeReservaActiva(UUID idUsuario, UUID idLibro) {
        return reservas.stream().anyMatch(r -> r.getIdUsuario().equals(idUsuario)
                && r.getIdLibro().equals(idLibro) && r.getEstado().equals("ACTIVA"));
    }

    public Optional<UUID> usuarioConReservaActiva(UUID idLibro) {
        return reservas.stream()
                .filter(r -> r.getIdLibro().equals(idLibro) && r.getEstado().equals("ACTIVA"))
                .map(Reserva::getIdUsuario)
                .findFirst();
    }

    public void marcarComoCompletada(UUID idUsuario, UUID idLibro) {
        reservas.stream()
                .filter(r -> r.getIdUsuario().equals(idUsuario) && r.getIdLibro().equals(idLibro) && r.getEstado().equals("ACTIVA"))
                .findFirst()
                .ifPresent(r -> r.setEstado("COMPLETADA"));
    }
}