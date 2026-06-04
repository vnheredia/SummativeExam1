package MonolitoCapas.Biblioteca.src.com.biblioteca.service;

import MonolitoCapas.Biblioteca.src.com.biblioteca.model.Reserva;
import MonolitoCapas.Biblioteca.src.com.biblioteca.repository.ReservaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class ReservaService {
    private final ReservaRepository reservaRepository = ReservaRepository.getInstance();
    private final UsuarioService usuarioService;
    private final LibroService libroService;
    private final MultaService multaService;

    public ReservaService(UsuarioService usuarioService, LibroService libroService, MultaService multaService) {
        this.usuarioService = usuarioService;
        this.libroService = libroService;
        this.multaService = multaService;
    }

    /**
     * Crea una reserva.
     * @return String en formato "OK|ID_RESERVA" o mensaje de error.
     */
    public String reservarLibro(UUID idUsuario, UUID idLibro) {
        if (usuarioService.buscarPorId(idUsuario) == null) return "Usuario no existe";
        if (multaService.tieneMultasPendientes(idUsuario)) return "Usuario tiene multas pendientes";
        if (libroService.buscarPorId(idLibro) == null) return "Libro no encontrado";
        if (reservaRepository.existeReservaActiva(idUsuario, idLibro))
            return "Ya tiene una reserva activa para este libro";

        UUID idReserva = UUID.randomUUID();
        reservaRepository.save(new Reserva(idReserva, idUsuario, idLibro, LocalDate.now()));
        return "OK|" + idReserva;
    }

    /**
     * Cancela una reserva por su ID.
     * @return "OK" o mensaje de error.
     */
    public String cancelarReserva(UUID idReserva) {
        var reserva = reservaRepository.findById(idReserva).orElse(null);
        if (reserva == null) return "Reserva no encontrada";
        if (!reserva.getEstado().equals("ACTIVA")) return "La reserva ya no está activa";
        reserva.setEstado("CANCELADA");
        return "OK";
    }

    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }

    public UUID usuarioConReservaActiva(UUID idLibro) {
        return reservaRepository.usuarioConReservaActiva(idLibro).orElse(null);
    }

    public void marcarReservaCompletada(UUID idUsuario, UUID idLibro) {
        reservaRepository.marcarComoCompletada(idUsuario, idLibro);
    }
}