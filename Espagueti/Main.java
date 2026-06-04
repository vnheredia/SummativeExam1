package Espagueti;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Main {

    // ===== DATOS GLOBALES =====

    // Libros
    static ArrayList<String> codigosLibros = new ArrayList<>();
    static ArrayList<String> titulosLibros = new ArrayList<>();
    static ArrayList<String> autoresLibros = new ArrayList<>();
    static ArrayList<Integer> stockLibros = new ArrayList<>();

    // Usuarios
    static ArrayList<String> idsUsuarios = new ArrayList<>();
    static ArrayList<String> nombresUsuarios = new ArrayList<>();

    // Préstamos
    static ArrayList<String> idsPrestamos = new ArrayList<>();
    static ArrayList<String> usuariosPrestamos = new ArrayList<>();
    static ArrayList<String> librosPrestamos = new ArrayList<>();
    static ArrayList<String> estadosPrestamos = new ArrayList<>();
    static ArrayList<LocalDate> fechasPrestamo = new ArrayList<>();
    static ArrayList<LocalDate> fechasLimite = new ArrayList<>();

    // Multas: usuario -> monto pendiente
    static ArrayList<String> usuariosConMulta = new ArrayList<>();
    static ArrayList<Double> montosMulta = new ArrayList<>();

    // Reservas
    static ArrayList<String> idsReservas = new ArrayList<>();
    static ArrayList<String> usuariosReserva = new ArrayList<>();
    static ArrayList<String> librosReserva = new ArrayList<>();
    static ArrayList<LocalDate> fechasReserva = new ArrayList<>();
    static ArrayList<String> estadosReserva = new ArrayList<>(); 

    static Scanner sc = new Scanner(System.in);
    static int contadorPrestamos = 1;
    static int contadorReservas = 1;

    public static void main(String[] args) {
        int opcion;
        do {
            System.out.println("\n=================================");
            System.out.println(" SISTEMA DE GESTION BIBLIOTECA ");
            System.out.println("=================================");
            System.out.println("1. Registrar Libro");
            System.out.println("2. Mostrar Libros");
            System.out.println("3. Buscar Libro");
            System.out.println("4. Editar Libro");
            System.out.println("5. Eliminar Libro");
            System.out.println("6. Registrar Usuario");
            System.out.println("7. Mostrar Usuarios");
            System.out.println("8. Buscar Usuario");
            System.out.println("9. Editar Usuario");
            System.out.println("10. Eliminar Usuario");
            System.out.println("11. Prestar Libro");
            System.out.println("12. Devolver Libro");
            System.out.println("13. Mostrar Prestamos");
            System.out.println("14. Reservar Libro");
            System.out.println("15. Cancelar Reserva");
            System.out.println("16. Mostrar Reservas");
            System.out.println("17. Pagar Multas");
            System.out.println("18. Salir");
            System.out.print("Seleccione una opcion: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1: registrarLibro(); break;
                case 2: mostrarLibros(); break;
                case 3: buscarLibro(); break;
                case 4: editarLibro(); break;
                case 5: eliminarLibro(); break;
                case 6: registrarUsuario(); break;
                case 7: mostrarUsuarios(); break;
                case 8: buscarUsuario(); break;
                case 9: editarUsuario(); break;
                case 10: eliminarUsuario(); break;
                case 11: prestarLibro(); break;
                case 12: devolverLibro(); break;
                case 13: mostrarPrestamos(); break;
                case 14: reservarLibro(); break;
                case 15: cancelarReserva(); break;
                case 16: mostrarReservas(); break;
                case 17: pagarMultas(); break;
                case 18: System.out.println("Saliendo..."); break;
                default: System.out.println("Opcion invalida.");
            }
        } while (opcion != 18);
        sc.close();
    }

    // ==========================
    // LIBROS
    // ==========================

    public static void registrarLibro() {
        System.out.print("Codigo del libro: ");
        String codigo = sc.nextLine();
        if (codigosLibros.contains(codigo)) {
            System.out.println("Ya existe un libro con ese codigo."); 
            return;
        }
        System.out.print("Titulo: ");
        String titulo = sc.nextLine();
        if (titulo.trim().isEmpty()) { 
            System.out.println("El titulo es obligatorio.");
            return;
        }
        System.out.print("Autor: ");
        String autor = sc.nextLine();
        if (autor.trim().isEmpty()) { 
            System.out.println("El autor es obligatorio.");
            return;
        }
        System.out.print("Cantidad inicial: ");
        int stock = leerEntero();
        if (stock <= 0) { 
            System.out.println("El stock inicial debe ser mayor que cero.");
            return;
        }
        codigosLibros.add(codigo);
        titulosLibros.add(titulo);
        autoresLibros.add(autor);
        stockLibros.add(stock);
        System.out.println("Libro registrado correctamente.");
    }

    public static void mostrarLibros() {
        if (codigosLibros.isEmpty()) {
            System.out.println("No existen libros registrados.");
            return;
        }
        System.out.println("\n===== LIBROS =====");
        for (int i = 0; i < codigosLibros.size(); i++) {
            System.out.println(codigosLibros.get(i) + " | " + titulosLibros.get(i) + " | " + autoresLibros.get(i) + " | Stock: " + stockLibros.get(i));
        }
    }

    public static void buscarLibro() {
        System.out.print("Ingrese codigo del libro: ");
        String codigo = sc.nextLine();
        int pos = codigosLibros.indexOf(codigo);
        if (pos == -1) {
            System.out.println("Libro no encontrado.");
        } else {
            System.out.println("Codigo: " + codigosLibros.get(pos));
            System.out.println("Titulo: " + titulosLibros.get(pos));
            System.out.println("Autor: " + autoresLibros.get(pos));
            System.out.println("Stock: " + stockLibros.get(pos));
        }
    }

    public static void editarLibro() {
        System.out.print("Codigo del libro a editar: ");
        String codigo = sc.nextLine();
        int pos = codigosLibros.indexOf(codigo);
        if (pos == -1) {
            System.out.println("Libro no encontrado.");
            return;
        }
        System.out.print("Nuevo titulo: ");
        String titulo = sc.nextLine();
        if (titulo.trim().isEmpty()) {
            System.out.println("El titulo no puede quedar vacio.");
            return;
        }
        System.out.print("Nuevo autor: ");
        String autor = sc.nextLine();
        if (autor.trim().isEmpty()) {
            System.out.println("El autor no puede quedar vacio.");
            return;
        }
        System.out.print("Nuevo stock: ");
        int nuevoStock = leerEntero();
        if (nuevoStock < 0) { 
            System.out.println("El stock no puede ser negativo.");
            return;
        }
        titulosLibros.set(pos, titulo);
        autoresLibros.set(pos, autor);
        stockLibros.set(pos, nuevoStock);
        System.out.println("Libro actualizado.");
    }

    public static void eliminarLibro() {
        System.out.print("Codigo del libro a eliminar: ");
        String codigo = sc.nextLine();
        int pos = codigosLibros.indexOf(codigo);
        if (pos == -1) {
            System.out.println("Libro no encontrado.");
            return;
        }
        for (int i = 0; i < librosPrestamos.size(); i++) {
            if (librosPrestamos.get(i).equals(codigo) && estadosPrestamos.get(i).equals("ACTIVO")) {
                System.out.println("No se puede eliminar el libro porque tiene prestamos activos.");
                return;
            }
        }
        for (int i = 0; i < librosReserva.size(); i++) {
            if (librosReserva.get(i).equals(codigo) && estadosReserva.get(i).equals("ACTIVA")) {
                System.out.println("No se puede eliminar el libro porque tiene reservas activas.");
                return;
            }
        }
        codigosLibros.remove(pos);
        titulosLibros.remove(pos);
        autoresLibros.remove(pos);
        stockLibros.remove(pos);
        System.out.println("Libro eliminado.");
    }

    // ==========================
    // USUARIOS
    // ==========================

    public static void registrarUsuario() {
        System.out.print("ID Usuario: ");
        String id = sc.nextLine();
        if (idsUsuarios.contains(id)) {
            System.out.println("Ya existe un usuario con ese ID."); 
            return;
        }
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        if (nombre.trim().isEmpty()) { 
            System.out.println("El nombre es obligatorio.");
            return;
        }
        idsUsuarios.add(id);
        nombresUsuarios.add(nombre);
        System.out.println("Usuario registrado.");
    }

    public static void mostrarUsuarios() {
        if (idsUsuarios.isEmpty()) {
            System.out.println("No existen usuarios.");
            return;
        }
        System.out.println("\n===== USUARIOS =====");
        for (int i = 0; i < idsUsuarios.size(); i++) {
            System.out.println(idsUsuarios.get(i) + " | " + nombresUsuarios.get(i));
        }
    }

    public static void buscarUsuario() {
        System.out.print("ID Usuario: ");
        String id = sc.nextLine();
        int pos = idsUsuarios.indexOf(id);
        if (pos == -1) {
            System.out.println("Usuario no encontrado.");
        } else {
            System.out.println("ID: " + idsUsuarios.get(pos));
            System.out.println("Nombre: " + nombresUsuarios.get(pos));
        }
    }

    public static void editarUsuario() {
        System.out.print("ID Usuario a editar: ");
        String id = sc.nextLine();
        int pos = idsUsuarios.indexOf(id);
        if (pos == -1) {
            System.out.println("Usuario no encontrado.");
            return;
        }
        System.out.print("Nuevo nombre: ");
        String nombre = sc.nextLine();
        if (nombre.trim().isEmpty()) {
            System.out.println("El nombre no puede estar vacio.");
            return;
        }
        nombresUsuarios.set(pos, nombre);
        System.out.println("Usuario actualizado.");
    }

    public static void eliminarUsuario() {
        System.out.print("ID Usuario a eliminar: ");
        String id = sc.nextLine();
        int pos = idsUsuarios.indexOf(id);
        if (pos == -1) {
            System.out.println("Usuario no encontrado.");
            return;
        }
        for (int i = 0; i < usuariosPrestamos.size(); i++) {
            if (usuariosPrestamos.get(i).equals(id) && estadosPrestamos.get(i).equals("ACTIVO")) {
                System.out.println("No se puede eliminar el usuario porque tiene prestamos activos.");
                return;
            }
        }
        if (tieneMultasPendientes(id)) {
            System.out.println("No se puede eliminar el usuario porque tiene multas pendientes.");
            return;
        }
        idsUsuarios.remove(pos);
        nombresUsuarios.remove(pos);
        System.out.println("Usuario eliminado.");
    }

    // ==========================
    // PRESTAMOS
    // ==========================

    public static void prestarLibro() {
        System.out.print("ID Usuario: ");
        String idUsuario = sc.nextLine();
        if (!idsUsuarios.contains(idUsuario)) {
            System.out.println("Usuario no existe."); // RN11
            return;
        }
        if (tieneMultasPendientes(idUsuario)) {
            System.out.println("El usuario tiene multas pendientes. Debe pagarlas antes de solicitar un prestamo.");
            return;
        }
        int prestamosActivos = 0;
        for (int i = 0; i < usuariosPrestamos.size(); i++) {
            if (usuariosPrestamos.get(i).equals(idUsuario) && estadosPrestamos.get(i).equals("ACTIVO")) {
                prestamosActivos++;
            }
        }
        if (prestamosActivos >= 3) {
            System.out.println("El usuario ya tiene 3 prestamos activos.");
            return;
        }
        System.out.print("Codigo Libro: ");
        String codigoLibro = sc.nextLine();
        int posLibro = codigosLibros.indexOf(codigoLibro);
        if (posLibro == -1) {
            System.out.println("Libro no encontrado."); 
            return;
        }
        if (stockLibros.get(posLibro) <= 0) {
            System.out.println("No hay stock disponible.");
            return;
        }
        for (int i = 0; i < usuariosPrestamos.size(); i++) {
            if (usuariosPrestamos.get(i).equals(idUsuario) && librosPrestamos.get(i).equals(codigoLibro) && estadosPrestamos.get(i).equals("ACTIVO")) {
                System.out.println("El usuario ya tiene un prestamo activo de este libro.");
                return;
            }
        }
        String reservaActivaPor = usuarioConReservaActiva(codigoLibro);
        if (reservaActivaPor != null && !reservaActivaPor.equals(idUsuario)) {
            System.out.println("El libro esta reservado por otro usuario. No se puede prestar.");
            return;
        }
        marcarReservaCompletada(idUsuario, codigoLibro);
        stockLibros.set(posLibro, stockLibros.get(posLibro) - 1);
        String idPrestamo = "P" + contadorPrestamos++;
        idsPrestamos.add(idPrestamo);
        usuariosPrestamos.add(idUsuario);
        librosPrestamos.add(codigoLibro);
        estadosPrestamos.add("ACTIVO");
        LocalDate fechaPrestamo = LocalDate.now();
        LocalDate fechaLimite = fechaPrestamo.plusDays(14); // 14 días de plazo
        fechasPrestamo.add(fechaPrestamo);
        fechasLimite.add(fechaLimite);
        System.out.println("Prestamo registrado. ID: " + idPrestamo);
        System.out.println("Fecha prestamo: " + fechaPrestamo);
        System.out.println("Fecha limite devolucion: " + fechaLimite);
    }

    public static void devolverLibro() {
        System.out.print("ID Prestamo: ");
        String idPrestamo = sc.nextLine();
        int pos = idsPrestamos.indexOf(idPrestamo);
        if (pos == -1) {
            System.out.println("Prestamo no encontrado."); 
            return;
        }
        if (estadosPrestamos.get(pos).equals("DEVUELTO")) {
            System.out.println("El prestamo ya fue devuelto."); 
            return;
        }
        LocalDate fechaDevolucion = LocalDate.now();
        LocalDate fechaLimite = fechasLimite.get(pos);
        if (fechaDevolucion.isAfter(fechaLimite)) {
            long diasRetraso = ChronoUnit.DAYS.between(fechaLimite, fechaDevolucion);
            double multa = diasRetraso * 1.0; // 1€ por día
            String idUsuario = usuariosPrestamos.get(pos);
            agregarMulta(idUsuario, multa);
            System.out.println("Devolucion fuera de plazo. Multa generada: " + multa + "€");
        }
        estadosPrestamos.set(pos, "DEVUELTO");
        String codigoLibro = librosPrestamos.get(pos);
        int posLibro = codigosLibros.indexOf(codigoLibro);
        if (posLibro != -1) {
            stockLibros.set(posLibro, stockLibros.get(posLibro) + 1);
        }
        System.out.println("Libro devuelto correctamente.");
    }

    public static void mostrarPrestamos() {
        if (idsPrestamos.isEmpty()) {
            System.out.println("No existen prestamos.");
            return;
        }
        System.out.println("\n===== PRESTAMOS =====");
        for (int i = 0; i < idsPrestamos.size(); i++) {
            System.out.println(idsPrestamos.get(i) + " | Usuario: " + usuariosPrestamos.get(i) +
                    " | Libro: " + librosPrestamos.get(i) + " | Estado: " + estadosPrestamos.get(i) +
                    " | Fecha prestamo: " + fechasPrestamo.get(i) + " | Fecha limite: " + fechasLimite.get(i));
        }
    }

    // ==========================
    //          RESERVAS 
    // ==========================

    public static void reservarLibro() {
        System.out.print("ID Usuario: ");
        String idUsuario = sc.nextLine();
        if (!idsUsuarios.contains(idUsuario)) {
            System.out.println("Usuario no existe."); 
            return;
        }
        if (tieneMultasPendientes(idUsuario)) {
            System.out.println("No puede reservar porque tiene multas pendientes.");
            return;
        }
        System.out.print("Codigo Libro: ");
        String codigoLibro = sc.nextLine();
        int posLibro = codigosLibros.indexOf(codigoLibro);
        if (posLibro == -1) {
            System.out.println("Libro no encontrado.");
            return;
        }
        if (existeReservaActiva(idUsuario, codigoLibro)) {
            System.out.println("Ya tiene una reserva activa para este libro.");
            return;
        }
        String idReserva = "R" + contadorReservas++;
        idsReservas.add(idReserva);
        usuariosReserva.add(idUsuario);
        librosReserva.add(codigoLibro);
        fechasReserva.add(LocalDate.now());
        estadosReserva.add("ACTIVA");
        System.out.println("Reserva registrada. ID: " + idReserva);
    }

    public static void cancelarReserva() {
        System.out.print("ID Reserva: ");
        String idReserva = sc.nextLine();
        int pos = idsReservas.indexOf(idReserva);
        if (pos == -1) {
            System.out.println("Reserva no encontrada.");
            return;
        }
        if (!estadosReserva.get(pos).equals("ACTIVA")) {
            System.out.println("La reserva ya no esta activa.");
            return;
        }
        estadosReserva.set(pos, "CANCELADA");
        System.out.println("Reserva cancelada.");
    }

    public static void mostrarReservas() {
        if (idsReservas.isEmpty()) {
            System.out.println("No hay reservas.");
            return;
        }
        System.out.println("\n===== RESERVAS =====");
        for (int i = 0; i < idsReservas.size(); i++) {
            System.out.println(idsReservas.get(i) + " | Usuario: " + usuariosReserva.get(i) +
                    " | Libro: " + librosReserva.get(i) + " | Fecha: " + fechasReserva.get(i) +
                    " | Estado: " + estadosReserva.get(i));
        }
    }

    // ==========================
    //          MULTAS 
    // ==========================

    public static void pagarMultas() {
        System.out.print("ID Usuario: ");
        String idUsuario = sc.nextLine();
        if (!idsUsuarios.contains(idUsuario)) {
            System.out.println("Usuario no existe.");
            return;
        }
        int idx = usuariosConMulta.indexOf(idUsuario);
        if (idx == -1 || montosMulta.get(idx) <= 0) {
            System.out.println("El usuario no tiene multas pendientes.");
            return;
        }
        double monto = montosMulta.get(idx);
        System.out.printf("Multa pendiente: %.2f€\n", monto);
        System.out.print("Desea pagar el total? (S/N): ");
        String resp = sc.nextLine();
        if (resp.equalsIgnoreCase("S")) {
            montosMulta.set(idx, 0.0);
            System.out.println("Multa pagada. Ahora puede solicitar prestamos.");
        } else {
            System.out.println("Pago cancelado.");
        }
    }

    private static boolean tieneMultasPendientes(String idUsuario) {
        int idx = usuariosConMulta.indexOf(idUsuario);
        return idx != -1 && montosMulta.get(idx) > 0;
    }

    private static void agregarMulta(String idUsuario, double monto) {
        int idx = usuariosConMulta.indexOf(idUsuario);
        if (idx == -1) {
            usuariosConMulta.add(idUsuario);
            montosMulta.add(monto);
        } else {
            montosMulta.set(idx, montosMulta.get(idx) + monto);
        }
    }

    private static boolean existeReservaActiva(String idUsuario, String codigoLibro) {
        for (int i = 0; i < usuariosReserva.size(); i++) {
            if (usuariosReserva.get(i).equals(idUsuario) && librosReserva.get(i).equals(codigoLibro) && estadosReserva.get(i).equals("ACTIVA")) {
                return true;
            }
        }
        return false;
    }

    private static String usuarioConReservaActiva(String codigoLibro) {
        for (int i = 0; i < librosReserva.size(); i++) {
            if (librosReserva.get(i).equals(codigoLibro) && estadosReserva.get(i).equals("ACTIVA")) {
                return usuariosReserva.get(i);
            }
        }
        return null;
    }

    private static void marcarReservaCompletada(String idUsuario, String codigoLibro) {
        for (int i = 0; i < usuariosReserva.size(); i++) {
            if (usuariosReserva.get(i).equals(idUsuario) && librosReserva.get(i).equals(codigoLibro) && estadosReserva.get(i).equals("ACTIVA")) {
                estadosReserva.set(i, "COMPLETADA");
                break;
            }
        }
    }

    // ==========================
    // UTILIDAD
    // ==========================

    public static int leerEntero() {
        while (!sc.hasNextInt()) {
            System.out.print("Ingrese un numero valido: ");
            sc.next();
        }
        int valor = sc.nextInt();
        sc.nextLine();
        return valor;
    }
}