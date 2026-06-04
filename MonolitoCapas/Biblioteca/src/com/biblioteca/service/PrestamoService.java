package MonolitoCapas.Biblioteca.src.com.biblioteca.service;

import MonolitoCapas.Biblioteca.src.com.biblioteca.model.Prestamo;
import MonolitoCapas.Biblioteca.src.com.biblioteca.repository.PrestamoRepository;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

public class PrestamoService {
    private final PrestamoRepository prestamoRepository = PrestamoRepository.getInstance();
    private final LibroService libroService;
    private final UsuarioService usuarioService;
    private final MultaService multaService;
    private final ReservaService reservaService;

    public PrestamoService(LibroService libroService, UsuarioService usuarioService,
                           MultaService multaService, ReservaService reservaService) {
        this.libroService = libroService;
        this.usuarioService = usuarioService;
        this.multaService = multaService;
        this.reservaService = reservaService;
    }

    /**
     * Realiza un préstamo.
     * @return String en formato "OK|ID_PRESTAMO|FECHA_PRESTAMO|FECHA_LIMITE" o mensaje de error.
     */
    public String prestarLibro(UUID idUsuario, UUID idLibro) {
        // Validar usuario
        if (usuarioService.buscarPorId(idUsuario) == null) return "Usuario no existe";
        if (multaService.tieneMultasPendientes(idUsuario)) return "Usuario tiene multas pendientes";
        if (prestamoRepository.findActivosByUsuario(idUsuario).size() >= 3) return "Ya tiene 3 préstamos activos";

        var libro = libroService.buscarPorId(idLibro);
        if (libro == null) return "Libro no encontrado";
        if (libro.getStock() <= 0) return "No hay stock disponible";
        if (prestamoRepository.existePrestamoActivoUsuarioLibro(idUsuario, idLibro))
            return "Ya tiene un préstamo activo de este libro";

        // Verificar reserva activa de otro usuario
        UUID usuarioReserva = reservaService.usuarioConReservaActiva(idLibro);
        if (usuarioReserva != null && !usuarioReserva.equals(idUsuario))
            return "El libro está reservado por otro usuario";

        // Si el mismo usuario tenía reserva, marcarla como completada
        reservaService.marcarReservaCompletada(idUsuario, idLibro);

        // Descontar stock
        libro.setStock(libro.getStock() - 1);

        // Crear préstamo
        UUID idPrestamo = UUID.randomUUID();
        LocalDate fechaPrestamo = LocalDate.now();
        LocalDate fechaLimite = fechaPrestamo.plusDays(14);
        prestamoRepository.save(new Prestamo(idPrestamo, idUsuario, idLibro, fechaPrestamo, fechaLimite));
        return "OK|" + idPrestamo + "|" + fechaPrestamo + "|" + fechaLimite;
    }

    /**
     * Devuelve un libro a partir del ID del préstamo.
     * @return "OK" o mensaje de error.
     */
    public String devolverLibro(UUID idPrestamo) {
        var prestamo = prestamoRepository.findById(idPrestamo).orElse(null);
        if (prestamo == null) return "Préstamo no encontrado";
        if (prestamo.getEstado().equals("DEVUELTO")) return "El préstamo ya fue devuelto";

        LocalDate fechaDevolucion = LocalDate.now();
        LocalDate fechaLimite = prestamo.getFechaLimite();
        if (fechaDevolucion.isAfter(fechaLimite)) {
            long diasRetraso = ChronoUnit.DAYS.between(fechaLimite, fechaDevolucion);
            double multa = diasRetraso * 1.0;
            multaService.agregarMulta(prestamo.getIdUsuario(), multa);
            System.out.println("Devolución fuera de plazo. Multa generada: " + multa + "€");
        }

        prestamo.setEstado("DEVUELTO");
        // Aumentar stock del libro
        var libro = libroService.buscarPorId(prestamo.getIdLibro());
        if (libro != null) libro.setStock(libro.getStock() + 1);
        return "OK";
    }

    public List<Prestamo> listarPrestamos() {
        return prestamoRepository.findAll();
    }

    public boolean tienePrestamosActivos(UUID idUsuario) {
        return !prestamoRepository.findActivosByUsuario(idUsuario).isEmpty();
    }
}