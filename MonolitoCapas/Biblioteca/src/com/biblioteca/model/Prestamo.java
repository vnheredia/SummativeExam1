package MonolitoCapas.Biblioteca.src.com.biblioteca.model;

import java.time.LocalDate;
import java.util.UUID;

public class Prestamo {
    private UUID id;
    private UUID idUsuario;
    private UUID idLibro;   // ahora referencia a UUID del libro
    private String estado;
    private LocalDate fechaPrestamo;
    private LocalDate fechaLimite;

    public Prestamo(UUID id, UUID idUsuario, UUID idLibro, LocalDate fechaPrestamo, LocalDate fechaLimite) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idLibro = idLibro;
        this.estado = "ACTIVO";
        this.fechaPrestamo = fechaPrestamo;
        this.fechaLimite = fechaLimite;
    }

    // Getters y Setters
    public UUID getId() { return id; }
    public UUID getIdUsuario() { return idUsuario; }
    public UUID getIdLibro() { return idLibro; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public LocalDate getFechaLimite() { return fechaLimite; }
}