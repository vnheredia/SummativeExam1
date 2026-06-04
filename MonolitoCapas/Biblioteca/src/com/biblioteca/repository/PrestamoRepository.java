package MonolitoCapas.Biblioteca.src.com.biblioteca.repository;

import MonolitoCapas.Biblioteca.src.com.biblioteca.model.Prestamo;
import java.util.*;

public class PrestamoRepository {
    private static PrestamoRepository instance;
    private final List<Prestamo> prestamos = new ArrayList<>();

    private PrestamoRepository() {}

    public static PrestamoRepository getInstance() {
        if (instance == null) instance = new PrestamoRepository();
        return instance;
    }

    public void save(Prestamo prestamo) {
        prestamos.add(prestamo);
    }

    public List<Prestamo> findAll() {
        return new ArrayList<>(prestamos);
    }

    public Optional<Prestamo> findById(UUID id) {
        return prestamos.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    public List<Prestamo> findByUsuario(UUID idUsuario) {
        return prestamos.stream().filter(p -> p.getIdUsuario().equals(idUsuario)).toList();
    }

    public List<Prestamo> findByLibro(UUID idLibro) {
        return prestamos.stream().filter(p -> p.getIdLibro().equals(idLibro)).toList();
    }

    public List<Prestamo> findActivosByUsuario(UUID idUsuario) {
        return prestamos.stream()
                .filter(p -> p.getIdUsuario().equals(idUsuario) && p.getEstado().equals("ACTIVO"))
                .toList();
    }

    public boolean existePrestamoActivoUsuarioLibro(UUID idUsuario, UUID idLibro) {
        return prestamos.stream().anyMatch(p -> p.getIdUsuario().equals(idUsuario)
                && p.getIdLibro().equals(idLibro) && p.getEstado().equals("ACTIVO"));
    }

    public boolean tienePrestamosActivos(UUID idUsuario) {
        return prestamos.stream().anyMatch(p -> p.getIdUsuario().equals(idUsuario) && p.getEstado().equals("ACTIVO"));
    }
}