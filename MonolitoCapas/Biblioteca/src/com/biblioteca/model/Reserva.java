package MonolitoCapas.Biblioteca.src.com.biblioteca.model;

import java.time.LocalDate;
import java.util.UUID;

public class Reserva {
    private UUID id;
    private UUID idUsuario;
    private UUID idLibro;
    private LocalDate fechaReserva;
    private String estado;

    public Reserva(UUID id, UUID idUsuario, UUID idLibro, LocalDate fechaReserva) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idLibro = idLibro;
        this.fechaReserva = fechaReserva;
        this.estado = "ACTIVA";
    }

    // Getters y Setters
    public UUID getId() { return id; }
    public UUID getIdUsuario() { return idUsuario; }
    public UUID getIdLibro() { return idLibro; }
    public LocalDate getFechaReserva() { return fechaReserva; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}