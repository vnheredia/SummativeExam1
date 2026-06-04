package MonolitoCapas.Biblioteca.src.com.biblioteca.service;

import java.util.List;
import MonolitoCapas.Biblioteca.src.com.biblioteca.model.Libro;
import MonolitoCapas.Biblioteca.src.com.biblioteca.repository.LibroRepository;
import java.util.UUID;

public class LibroService {
    private final LibroRepository libroRepository = LibroRepository.getInstance();

    public boolean registrarLibro(String codigo, String titulo, String autor, int stock) {
        if (libroRepository.existsByCodigo(codigo)) return false;
        if (titulo.trim().isEmpty() || autor.trim().isEmpty() || stock <= 0) return false;
        UUID id = UUID.randomUUID();
        libroRepository.save(new Libro(id, codigo, titulo, autor, stock));
        return true;
    }

    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }

    public Libro buscarPorCodigo(String codigo) {
        return libroRepository.findByCodigo(codigo).orElse(null);
    }

    public Libro buscarPorId(UUID id) {
        return libroRepository.findById(id).orElse(null);
    }

    public boolean editarLibro(UUID id, String nuevoTitulo, String nuevoAutor, int nuevoStock) {
        Libro libro = libroRepository.findById(id).orElse(null);
        if (libro == null) return false;
        if (nuevoTitulo.trim().isEmpty() || nuevoAutor.trim().isEmpty() || nuevoStock < 0) return false;
        libro.setTitulo(nuevoTitulo);
        libro.setAutor(nuevoAutor);
        libro.setStock(nuevoStock);
        return true;
    }

    public boolean eliminarLibro(UUID id) {
        Libro libro = libroRepository.findById(id).orElse(null);
        if (libro == null) return false;
        libroRepository.delete(libro);
        return true;
    }
}