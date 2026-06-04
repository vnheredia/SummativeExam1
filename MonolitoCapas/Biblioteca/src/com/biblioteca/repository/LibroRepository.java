package MonolitoCapas.Biblioteca.src.com.biblioteca.repository;

import MonolitoCapas.Biblioteca.src.com.biblioteca.model.Libro;

import java.util.*;

public class LibroRepository {
    private static LibroRepository instance;
    private final Map<UUID, Libro> libros = new HashMap<>();

    private LibroRepository() {}

    public static LibroRepository getInstance() {
        if (instance == null) instance = new LibroRepository();
        return instance;
    }

    public void save(Libro libro) {
        libros.put(libro.getId(), libro);
    }

    public List<Libro> findAll() {
        return new ArrayList<>(libros.values());
    }

    public Optional<Libro> findById(UUID id) {
        return Optional.ofNullable(libros.get(id));
    }

    public Optional<Libro> findByCodigo(String codigo) {
        return libros.values().stream()
                .filter(l -> l.getCodigo().equals(codigo))
                .findFirst();
    }

    public void delete(Libro libro) {
        libros.remove(libro.getId());
    }

    public boolean existsByCodigo(String codigo) {
        return libros.values().stream().anyMatch(l -> l.getCodigo().equals(codigo));
    }

    public boolean existsById(UUID id) {
        return libros.containsKey(id);
    }
}