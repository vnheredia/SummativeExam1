package MonolitoCapas.Biblioteca.src.com.biblioteca.model;
import java.util.UUID;

public class Usuario {
    private UUID id;
    private String nombre;

    public Usuario(UUID id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}