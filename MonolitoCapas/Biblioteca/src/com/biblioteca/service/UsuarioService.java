package MonolitoCapas.Biblioteca.src.com.biblioteca.service;

import java.util.List;
import MonolitoCapas.Biblioteca.src.com.biblioteca.model.Usuario;
import MonolitoCapas.Biblioteca.src.com.biblioteca.repository.UsuarioRepository;
import java.util.UUID;

public class UsuarioService {
    private final UsuarioRepository usuarioRepository = UsuarioRepository.getInstance();

    public boolean registrarUsuario(UUID id, String nombre) {
        if (usuarioRepository.existsById(id)) return false;
        if (nombre.trim().isEmpty()) return false;
        usuarioRepository.save(new Usuario(id, nombre));
        return true;
    }

    public boolean registrarUsuario(String nombre) {
        return registrarUsuario(UUID.randomUUID(), nombre);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(UUID id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public boolean editarUsuario(UUID id, String nuevoNombre) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null || nuevoNombre.trim().isEmpty()) return false;
        usuario.setNombre(nuevoNombre);
        return true;
    }

    public boolean eliminarUsuario(UUID id, PrestamoService prestamoService, MultaService multaService) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) return false;
        if (prestamoService.tienePrestamosActivos(id)) return false;
        if (multaService.tieneMultasPendientes(id)) return false;
        usuarioRepository.delete(usuario);
        return true;
    }
}