package MonolitoCapas.Biblioteca.src.com.biblioteca.repository;

import MonolitoCapas.Biblioteca.src.com.biblioteca.model.Usuario;

import java.util.*;

public class UsuarioRepository {
    private static UsuarioRepository instance;
    private final Map<UUID, Usuario> usuarios = new HashMap<>();

    private UsuarioRepository() {}

    public static UsuarioRepository getInstance() {
        if (instance == null) instance = new UsuarioRepository();
        return instance;
    }

    public void save(Usuario usuario) {
        usuarios.put(usuario.getId(), usuario);
    }

    public List<Usuario> findAll() {
        return new ArrayList<>(usuarios.values());
    }

    public Optional<Usuario> findById(UUID id) {
        return Optional.ofNullable(usuarios.get(id));
    }

    public void delete(Usuario usuario) {
        usuarios.remove(usuario.getId());
    }

    public boolean existsById(UUID id) {
        return usuarios.containsKey(id);
    }
}