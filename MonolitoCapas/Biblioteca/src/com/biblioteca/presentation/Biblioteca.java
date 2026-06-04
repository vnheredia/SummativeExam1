package MonolitoCapas.Biblioteca.src.com.biblioteca.presentation;

import MonolitoCapas.Biblioteca.src.com.biblioteca.model.*;
import MonolitoCapas.Biblioteca.src.com.biblioteca.service.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class Biblioteca extends JFrame {
    private LibroService libroService;
    private UsuarioService usuarioService;
    private PrestamoService prestamoService;
    private ReservaService reservaService;
    private MultaService multaService;

    // Tablas y modelos
    private DefaultTableModel tableModelLibros, tableModelUsuarios, tableModelPrestamos, tableModelReservas;
    private JTable tableLibros, tableUsuarios, tablePrestamos, tableReservas;

    // Paleta de colores moderna y sofisticada (dark navy + gold accent)
    private final Color COLOR_BG_DARK       = new Color(13, 17, 28);
    private final Color COLOR_BG_PANEL      = new Color(20, 26, 42);
    private final Color COLOR_BG_CARD       = new Color(28, 36, 58);
    private final Color COLOR_ACCENT_GOLD   = new Color(212, 175, 55);
    private final Color COLOR_ACCENT_LIGHT  = new Color(255, 220, 100);
    private final Color COLOR_TEXT_PRIMARY  = new Color(230, 232, 240);
    private final Color COLOR_TEXT_MUTED    = new Color(130, 140, 165);
    private final Color COLOR_BORDER        = new Color(45, 55, 80);
    private final Color COLOR_ROW_ALT       = new Color(24, 32, 50);
    private final Color COLOR_ROW_SEL       = new Color(212, 175, 55, 60);
    private final Color COLOR_BTN_BLUE      = new Color(41, 98, 255);
    private final Color COLOR_BTN_GREEN     = new Color(16, 185, 129);
    private final Color COLOR_BTN_RED       = new Color(239, 68, 68);
    private final Color COLOR_BTN_PURPLE    = new Color(139, 92, 246);
    private final Color COLOR_BTN_YELLOW    = new Color(245, 158, 11);
    private final Color COLOR_BTN_GRAY      = new Color(71, 85, 105);

    private final Font FONT_TITLE       = new Font("Georgia", Font.BOLD, 30);
    private final Font FONT_SUBTITLE    = new Font("Georgia", Font.ITALIC, 12);
    private final Font FONT_TAB         = new Font("Trebuchet MS", Font.BOLD, 13);
    private final Font FONT_TABLE_HDR   = new Font("Trebuchet MS", Font.BOLD, 12);
    private final Font FONT_TABLE_CELL  = new Font("Trebuchet MS", Font.PLAIN, 13);
    private final Font FONT_BTN         = new Font("Trebuchet MS", Font.BOLD, 12);
    private final Font FONT_SECTION     = new Font("Georgia", Font.BOLD, 14);

    public Biblioteca() {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
            // Ajustes globales Nimbus para tema oscuro
            UIManager.put("control",            COLOR_BG_PANEL);
            UIManager.put("nimbusBase",         COLOR_BG_DARK);
            UIManager.put("nimbusFocus",        COLOR_ACCENT_GOLD);
            UIManager.put("nimbusLightBackground", COLOR_BG_CARD);
            UIManager.put("text",               COLOR_TEXT_PRIMARY);
            UIManager.put("TabbedPane.contentAreaColor", COLOR_BG_PANEL);
            UIManager.put("TabbedPane.selected", COLOR_BG_CARD);
        } catch (Exception e) {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
            catch (Exception ignored) {}
        }

        this.libroService    = new LibroService();
        this.usuarioService  = new UsuarioService();
        this.multaService    = new MultaService();
        this.reservaService  = new ReservaService(usuarioService, libroService, multaService);
        this.prestamoService = new PrestamoService(libroService, usuarioService, multaService, reservaService);

        setTitle("Sistema de Gestión de Biblioteca");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1440, 880);
        setMinimumSize(new Dimension(1100, 720));
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_BG_DARK);

        // ── Panel raíz ──────────────────────────────────────────────────────────
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(COLOR_BG_DARK);
        root.setBorder(new EmptyBorder(0, 0, 0, 0));

        // ── Cabecera ─────────────────────────────────────────────────────────────
        root.add(crearCabecera(), BorderLayout.NORTH);

        // ── Pestañas ─────────────────────────────────────────────────────────────
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setFont(FONT_TAB);
        tabbedPane.setBackground(COLOR_BG_DARK);
        tabbedPane.setForeground(COLOR_TEXT_PRIMARY);
        tabbedPane.setBorder(new EmptyBorder(0, 0, 0, 0));

        tabbedPane.addTab("  📚  Libros  ",    crearPanelLibros());
        tabbedPane.addTab("  👤  Usuarios  ",   crearPanelUsuarios());
        tabbedPane.addTab("  📘  Préstamos  ",  crearPanelPrestamos());
        tabbedPane.addTab("  🔖  Reservas  ",   crearPanelReservas());
        tabbedPane.addTab("  💰  Multas  ",     crearPanelMultas());

        JPanel contentWrapper = new JPanel(new BorderLayout());
        contentWrapper.setBackground(COLOR_BG_DARK);
        contentWrapper.setBorder(new EmptyBorder(0, 18, 18, 18));
        contentWrapper.add(tabbedPane, BorderLayout.CENTER);
        root.add(contentWrapper, BorderLayout.CENTER);

        // ── Pie de página ─────────────────────────────────────────────────────────
        root.add(crearFooter(), BorderLayout.SOUTH);

        add(root);
        setVisible(true);
    }

    private JTabbedPane tabbedPane;

    // ══════════════════════════════════════════════════════════════════════════
    //  CABECERA
    // ══════════════════════════════════════════════════════════════════════════
    private JPanel crearCabecera() {
        JPanel header = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Gradiente horizontal oscuro con toque dorado
                GradientPaint gp = new GradientPaint(
                    0, 0, new Color(13, 17, 28),
                    getWidth(), 0, new Color(25, 30, 50));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                // Línea dorada inferior
                g2.setColor(COLOR_ACCENT_GOLD);
                g2.setStroke(new BasicStroke(2f));
                g2.drawLine(0, getHeight() - 2, getWidth(), getHeight() - 2);
                g2.dispose();
            }
        };
        header.setPreferredSize(new Dimension(0, 90));
        header.setBorder(new EmptyBorder(16, 28, 16, 28));

        // Icono + título
        JLabel icon = new JLabel("📖");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 38));
        icon.setBorder(new EmptyBorder(0, 0, 0, 16));

        JPanel textBlock = new JPanel();
        textBlock.setOpaque(false);
        textBlock.setLayout(new BoxLayout(textBlock, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Sistema de Gestión de Biblioteca");
        title.setFont(FONT_TITLE);
        title.setForeground(COLOR_ACCENT_GOLD);
        JLabel subtitle = new JLabel("Gestione libros, usuarios, préstamos, reservas y multas desde un solo lugar");
        subtitle.setFont(FONT_SUBTITLE);
        subtitle.setForeground(COLOR_TEXT_MUTED);
        textBlock.add(title);
        textBlock.add(Box.createVerticalStrut(4));
        textBlock.add(subtitle);

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        left.setOpaque(false);
        left.add(icon);
        left.add(textBlock);
        header.add(left, BorderLayout.WEST);

        return header;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  FOOTER
    // ══════════════════════════════════════════════════════════════════════════
    private JPanel crearFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 6));
        footer.setBackground(new Color(10, 13, 22));
        footer.setBorder(new MatteBorder(1, 0, 0, 0, COLOR_BORDER));
        JLabel lbl = new JLabel("© 2025 Biblioteca  —  Todos los derechos reservados");
        lbl.setFont(new Font("Trebuchet MS", Font.PLAIN, 11));
        lbl.setForeground(COLOR_TEXT_MUTED);
        footer.add(lbl);
        return footer;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  UTILIDADES VISUALES
    // ══════════════════════════════════════════════════════════════════════════

    private void configurarTabla(JTable table) {
        table.setFont(FONT_TABLE_CELL);
        table.setRowHeight(36);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setBackground(COLOR_BG_CARD);
        table.setForeground(COLOR_TEXT_PRIMARY);
        table.setSelectionBackground(COLOR_ROW_SEL);
        table.setSelectionForeground(COLOR_ACCENT_LIGHT);
        table.setFillsViewportHeight(true);

        // Header
        JTableHeader header = table.getTableHeader();
        header.setFont(FONT_TABLE_HDR);
        header.setBackground(new Color(18, 22, 38));
        header.setForeground(COLOR_ACCENT_GOLD);
        header.setPreferredSize(new Dimension(header.getWidth(), 42));
        header.setBorder(new MatteBorder(0, 0, 2, 0, COLOR_ACCENT_GOLD));
        header.setReorderingAllowed(false);

        // Renderer de filas alternadas
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable t, Object value, boolean isSelected,
                    boolean hasFocus, int row, int col) {
                super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, col);
                setFont(FONT_TABLE_CELL);
                setBorder(new EmptyBorder(0, 14, 0, 10));
                if (isSelected) {
                    setBackground(COLOR_ROW_SEL);
                    setForeground(COLOR_ACCENT_LIGHT);
                } else {
                    setBackground(row % 2 == 0 ? COLOR_BG_CARD : COLOR_ROW_ALT);
                    setForeground(COLOR_TEXT_PRIMARY);
                }
                return this;
            }
        };
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        // Hover effect
        table.addMouseMotionListener(new MouseAdapter() {
            private int lastRow = -1;
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row != lastRow) { lastRow = row; table.repaint(); }
            }
        });
    }

    /** Botón con esquinas redondeadas, hover y efecto press */
    private JButton crearBoton(String texto, Color colorFondo) {
        JButton btn = new JButton(texto) {
            private boolean hovered = false;
            private boolean pressed = false;
            {
                addMouseListener(new MouseAdapter() {
                    @Override public void mouseEntered(MouseEvent e) { hovered = true;  repaint(); }
                    @Override public void mouseExited (MouseEvent e) { hovered = false; repaint(); }
                    @Override public void mousePressed(MouseEvent e) { pressed = true;  repaint(); }
                    @Override public void mouseReleased(MouseEvent e){ pressed = false; repaint(); }
                });
            }
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color base = colorFondo;
                if (pressed) base = base.darker().darker();
                else if (hovered) base = base.brighter();
                g2.setColor(base);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
                // Borde sutil
                g2.setColor(base.brighter());
                g2.setStroke(new BasicStroke(1f));
                g2.draw(new RoundRectangle2D.Float(0.5f, 0.5f, getWidth()-1, getHeight()-1, 10, 10));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setForeground(Color.WHITE);
        btn.setFont(FONT_BTN);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setBorder(new EmptyBorder(10, 22, 10, 22));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(185, 42));
        return btn;
    }

    private JScrollPane crearScrollPane(JTable tabla, String titulo) {
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBackground(COLOR_BG_CARD);
        scroll.getViewport().setBackground(COLOR_BG_CARD);
        scroll.setBorder(BorderFactory.createTitledBorder(
            new LineBorder(COLOR_ACCENT_GOLD, 1),
            "  " + titulo + "  ",
            TitledBorder.LEFT, TitledBorder.TOP,
            FONT_SECTION, COLOR_ACCENT_GOLD));
        scroll.getVerticalScrollBar().setBackground(COLOR_BG_PANEL);
        return scroll;
    }

    private JPanel crearPanelBotones(JButton... botones) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 14, 14));
        panel.setBackground(COLOR_BG_PANEL);
        panel.setBorder(new MatteBorder(1, 0, 0, 0, COLOR_BORDER));
        for (JButton b : botones) panel.add(b);
        return panel;
    }

    private JPanel panelBase() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(COLOR_BG_PANEL);
        p.setBorder(new EmptyBorder(14, 14, 0, 14));
        return p;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  PANEL LIBROS
    // ══════════════════════════════════════════════════════════════════════════
    private JPanel crearPanelLibros() {
        JPanel panel = panelBase();

        tableModelLibros = new DefaultTableModel(new String[]{"ID", "Código", "Título", "Autor", "Stock"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tableLibros = new JTable(tableModelLibros);
        configurarTabla(tableLibros);
        panel.add(crearScrollPane(tableLibros, "Catálogo de Libros"), BorderLayout.CENTER);

        JButton btnAgregar  = crearBoton("＋  Agregar",   COLOR_BTN_BLUE);
        JButton btnEditar   = crearBoton("✎  Editar",     COLOR_BTN_GREEN);
        JButton btnEliminar = crearBoton("✕  Eliminar",   COLOR_BTN_RED);
        JButton btnBuscar   = crearBoton("🔍  Buscar",    COLOR_BTN_PURPLE);
        panel.add(crearPanelBotones(btnAgregar, btnEditar, btnEliminar, btnBuscar), BorderLayout.SOUTH);

        btnAgregar.addActionListener(e  -> agregarLibro());
        btnEditar.addActionListener(e   -> editarLibro());
        btnEliminar.addActionListener(e -> eliminarLibro());
        btnBuscar.addActionListener(e   -> buscarLibro());

        cargarLibrosTabla();
        return panel;
    }

    private void cargarLibrosTabla() {
        tableModelLibros.setRowCount(0);
        for (Libro l : libroService.listarLibros()) {
            tableModelLibros.addRow(new Object[]{
                l.getId().toString().substring(0, 8) + "...",
                l.getCodigo(),
                l.getTitulo(),
                l.getAutor(),
                l.getStock()
            });
        }
    }

    private void agregarLibro() {
        JTextField txtCodigo = styledField();
        JTextField txtTitulo = styledField();
        JTextField txtAutor  = styledField();
        JTextField txtStock  = styledField();
        Object[] message = {
            "Código:", txtCodigo,
            "Título:", txtTitulo,
            "Autor:",  txtAutor,
            "Stock:",  txtStock
        };
        int option = showDialog(message, "Agregar Libro");
        if (option == JOptionPane.OK_OPTION) {
            try {
                String codigo = txtCodigo.getText().trim();
                String titulo = txtTitulo.getText().trim();
                String autor  = txtAutor.getText().trim();
                int stock     = Integer.parseInt(txtStock.getText().trim());
                if (libroService.registrarLibro(codigo, titulo, autor, stock)) {
                    cargarLibrosTabla();
                    showSuccess("Libro agregado exitosamente");
                } else {
                    showError("Error: código duplicado o datos inválidos");
                }
            } catch (NumberFormatException ex) {
                showError("El stock debe ser un número entero");
            }
        }
    }

    private void editarLibro() {
        int selectedRow = tableLibros.getSelectedRow();
        if (selectedRow == -1) { showWarning("Seleccione un libro de la tabla"); return; }
        String codigo = (String) tableModelLibros.getValueAt(selectedRow, 1);
        Libro libro = libroService.buscarPorCodigo(codigo);
        if (libro == null) return;

        JTextField txtTitulo = styledField(libro.getTitulo());
        JTextField txtAutor  = styledField(libro.getAutor());
        JTextField txtStock  = styledField(String.valueOf(libro.getStock()));
        Object[] message = {"Título:", txtTitulo, "Autor:", txtAutor, "Stock:", txtStock};
        int option = showDialog(message, "Editar Libro");
        if (option == JOptionPane.OK_OPTION) {
            try {
                String titulo = txtTitulo.getText().trim();
                String autor  = txtAutor.getText().trim();
                int stock     = Integer.parseInt(txtStock.getText().trim());
                if (libroService.editarLibro(libro.getId(), titulo, autor, stock)) {
                    cargarLibrosTabla();
                    showSuccess("Libro actualizado");
                } else {
                    showError("Error al actualizar");
                }
            } catch (NumberFormatException ex) {
                showError("Stock inválido");
            }
        }
    }

    private void eliminarLibro() {
        int selectedRow = tableLibros.getSelectedRow();
        if (selectedRow == -1) { showWarning("Seleccione un libro"); return; }
        String codigo = (String) tableModelLibros.getValueAt(selectedRow, 1);
        Libro libro = libroService.buscarPorCodigo(codigo);
        if (libro == null) return;

        boolean tienePrestamos = prestamoService.listarPrestamos().stream()
                .anyMatch(p -> p.getIdLibro().equals(libro.getId()) && p.getEstado().equals("ACTIVO"));
        boolean tieneReservas = reservaService.listarReservas().stream()
                .anyMatch(r -> r.getIdLibro().equals(libro.getId()) && r.getEstado().equals("ACTIVA"));
        if (tienePrestamos || tieneReservas) {
            showError("No se puede eliminar: el libro tiene préstamos o reservas activas");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Eliminar permanentemente el libro «" + libro.getTitulo() + "»?",
            "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (libroService.eliminarLibro(libro.getId())) {
                cargarLibrosTabla(); showSuccess("Libro eliminado");
            } else { showError("Error al eliminar"); }
        }
    }

    private void buscarLibro() {
        String codigo = JOptionPane.showInputDialog(this, "Ingrese código del libro:");
        if (codigo != null && !codigo.trim().isEmpty()) {
            Libro libro = libroService.buscarPorCodigo(codigo.trim());
            if (libro == null) {
                JOptionPane.showMessageDialog(this, "No se encontró el libro con código: " + codigo,
                    "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
            } else {
                String msg = String.format(
                    "📕 Código: %s\n📖 Título: %s\n✍️ Autor: %s\n📚 Stock: %d",
                    libro.getCodigo(), libro.getTitulo(), libro.getAutor(), libro.getStock());
                JOptionPane.showMessageDialog(this, msg, "Libro encontrado", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  PANEL USUARIOS
    // ══════════════════════════════════════════════════════════════════════════
    private JPanel crearPanelUsuarios() {
        JPanel panel = panelBase();

        tableModelUsuarios = new DefaultTableModel(new String[]{"ID", "Nombre"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tableUsuarios = new JTable(tableModelUsuarios);
        configurarTabla(tableUsuarios);
        panel.add(crearScrollPane(tableUsuarios, "Registro de Usuarios"), BorderLayout.CENTER);

        JButton btnAgregar  = crearBoton("＋  Agregar",   COLOR_BTN_BLUE);
        JButton btnEditar   = crearBoton("✎  Editar",     COLOR_BTN_GREEN);
        JButton btnEliminar = crearBoton("✕  Eliminar",   COLOR_BTN_RED);
        JButton btnBuscar   = crearBoton("🔍  Buscar",    COLOR_BTN_PURPLE);
        panel.add(crearPanelBotones(btnAgregar, btnEditar, btnEliminar, btnBuscar), BorderLayout.SOUTH);

        btnAgregar.addActionListener(e  -> agregarUsuario());
        btnEditar.addActionListener(e   -> editarUsuario());
        btnEliminar.addActionListener(e -> eliminarUsuario());
        btnBuscar.addActionListener(e   -> buscarUsuario());

        cargarUsuariosTabla();
        return panel;
    }

    private void cargarUsuariosTabla() {
        tableModelUsuarios.setRowCount(0);
        for (Usuario u : usuarioService.listarUsuarios()) {
            tableModelUsuarios.addRow(new Object[]{
                u.getId().toString().substring(0, 8) + "...", u.getNombre()
            });
        }
    }

    private void agregarUsuario() {
        JTextField txtNombre = styledField();
        int option = showDialog(new Object[]{"Nombre completo:", txtNombre}, "Agregar Usuario");
        if (option == JOptionPane.OK_OPTION) {
            String nombre = txtNombre.getText().trim();
            if (nombre.isEmpty()) { showError("El nombre no puede estar vacío"); return; }
            UUID id = UUID.randomUUID();
            if (usuarioService.registrarUsuario(id, nombre)) {
                cargarUsuariosTabla();
                showSuccess("Usuario agregado con ID: " + id.toString().substring(0, 8) + "...");
            } else {
                showError("Error al registrar (ID duplicado inesperado)");
            }
        }
    }

    private void editarUsuario() {
        int row = tableUsuarios.getSelectedRow();
        if (row == -1) { showWarning("Seleccione un usuario"); return; }
        String nombreActual = (String) tableModelUsuarios.getValueAt(row, 1);
        String nuevoNombre = JOptionPane.showInputDialog(this, "Nuevo nombre:", nombreActual);
        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            List<Usuario> usuarios = usuarioService.listarUsuarios();
            UUID id = usuarios.get(row).getId();
            if (usuarioService.editarUsuario(id, nuevoNombre.trim())) {
                cargarUsuariosTabla(); showSuccess("Usuario actualizado");
            } else { showError("Error al actualizar"); }
        }
    }

    private void eliminarUsuario() {
        int row = tableUsuarios.getSelectedRow();
        if (row == -1) { showWarning("Seleccione un usuario"); return; }
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        Usuario u = usuarios.get(row);
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Eliminar al usuario «" + u.getNombre() + "»? Se perderán sus datos.",
            "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (usuarioService.eliminarUsuario(u.getId(), prestamoService, multaService)) {
                cargarUsuariosTabla(); showSuccess("Usuario eliminado");
            } else {
                showError("No se puede eliminar: tiene préstamos activos o multas pendientes");
            }
        }
    }

    private void buscarUsuario() {
        String nombre = JOptionPane.showInputDialog(this, "Ingrese nombre (o parte) del usuario:");
        if (nombre != null && !nombre.trim().isEmpty()) {
            List<Usuario> resultados = usuarioService.listarUsuarios().stream()
                    .filter(u -> u.getNombre().toLowerCase().contains(nombre.trim().toLowerCase()))
                    .toList();
            if (resultados.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontraron usuarios con: " + nombre,
                    "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
            } else {
                StringBuilder sb = new StringBuilder("Usuarios encontrados:\n");
                for (Usuario u : resultados) {
                    sb.append("• ").append(u.getNombre())
                      .append("  (ID: ").append(u.getId().toString().substring(0, 8)).append("...)\n");
                }
                JOptionPane.showMessageDialog(this, sb.toString(), "Resultados", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  PANEL PRÉSTAMOS
    // ══════════════════════════════════════════════════════════════════════════
    private JPanel crearPanelPrestamos() {
        JPanel panel = panelBase();

        tableModelPrestamos = new DefaultTableModel(
            new String[]{"ID Préstamo", "Usuario", "Libro", "Estado", "Fecha Préstamo", "Fecha Límite"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablePrestamos = new JTable(tableModelPrestamos);
        configurarTabla(tablePrestamos);
        panel.add(crearScrollPane(tablePrestamos, "Préstamos Registrados"), BorderLayout.CENTER);

        JButton btnPrestar   = crearBoton("📘  Prestar Libro",   COLOR_BTN_BLUE);
        JButton btnDevolver  = crearBoton("🔄  Devolver Libro",  COLOR_BTN_GREEN);
        JButton btnActualizar= crearBoton("↺  Actualizar",       COLOR_BTN_GRAY);
        panel.add(crearPanelBotones(btnPrestar, btnDevolver, btnActualizar), BorderLayout.SOUTH);

        btnPrestar.addActionListener(e    -> prestarLibroMejorado());
        btnDevolver.addActionListener(e   -> devolverLibro());
        btnActualizar.addActionListener(e -> cargarPrestamosTabla());

        cargarPrestamosTabla();
        return panel;
    }

    private void cargarPrestamosTabla() {
        tableModelPrestamos.setRowCount(0);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (Prestamo p : prestamoService.listarPrestamos()) {
            Usuario u = usuarioService.buscarPorId(p.getIdUsuario());
            Libro l   = libroService.buscarPorId(p.getIdLibro());
            String usuarioStr = (u != null) ? u.getNombre() : p.getIdUsuario().toString().substring(0, 8);
            String libroStr   = (l != null) ? l.getTitulo() : "Libro eliminado";
            tableModelPrestamos.addRow(new Object[]{
                p.getId().toString().substring(0, 8) + "...",
                usuarioStr, libroStr, p.getEstado(),
                p.getFechaPrestamo().format(dtf),
                p.getFechaLimite().format(dtf)
            });
        }
    }

    // ── Clases auxiliares combo ───────────────────────────────────────────────
    private static class UsuarioComboItem {
        private final Usuario usuario;
        public UsuarioComboItem(Usuario u) { usuario = u; }
        public Usuario getUsuario() { return usuario; }
        @Override public String toString() {
            return usuario.getNombre() + " (" + usuario.getId().toString().substring(0, 8) + "...)";
        }
    }

    private static class LibroComboItem {
        private final Libro libro;
        public LibroComboItem(Libro l) { libro = l; }
        public Libro getLibro() { return libro; }
        @Override public String toString() {
            return libro.getTitulo() + "  [Cód: " + libro.getCodigo() + "  |  Stock: " + libro.getStock() + "]";
        }
    }

    private void prestarLibroMejorado() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        List<Libro>   libros   = libroService.listarLibros();

        if (usuarios.isEmpty()) { showWarning("No hay usuarios registrados."); return; }
        if (libros.isEmpty())   { showWarning("No hay libros registrados."); return; }

        JComboBox<UsuarioComboItem> comboUsuarios = styledCombo();
        for (Usuario u : usuarios) comboUsuarios.addItem(new UsuarioComboItem(u));

        JComboBox<LibroComboItem> comboLibros = styledCombo();
        for (Libro l : libros) if (l.getStock() > 0) comboLibros.addItem(new LibroComboItem(l));
        if (comboLibros.getItemCount() == 0) { showWarning("No hay libros con stock disponible."); return; }

        JPanel p = new JPanel(new GridLayout(2, 2, 12, 12));
        p.setBorder(new EmptyBorder(16, 16, 16, 16));
        p.setBackground(COLOR_BG_CARD);
        p.add(styledLabel("👤 Usuario:")); p.add(comboUsuarios);
        p.add(styledLabel("📚 Libro:"));   p.add(comboLibros);

        int option = JOptionPane.showConfirmDialog(this, p, "Prestar Libro",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            UsuarioComboItem uItem = (UsuarioComboItem) comboUsuarios.getSelectedItem();
            LibroComboItem   lItem = (LibroComboItem)   comboLibros.getSelectedItem();
            if (uItem != null && lItem != null) {
                String resultado = prestamoService.prestarLibro(uItem.getUsuario().getId(), lItem.getLibro().getId());
                if (resultado.startsWith("OK|")) {
                    cargarPrestamosTabla();
                    showSuccess("Préstamo registrado con ID: " + resultado.split("\\|")[1].substring(0, 8) + "...");
                } else { showError(resultado); }
            }
        }
    }

    private void devolverLibro() {
        int row = tablePrestamos.getSelectedRow();
        if (row == -1) { showWarning("Seleccione un préstamo activo en la tabla"); return; }
        String estado = (String) tableModelPrestamos.getValueAt(row, 3);
        if (!estado.equals("ACTIVO")) { showWarning("Este préstamo ya fue devuelto"); return; }
        String idTruncado = (String) tableModelPrestamos.getValueAt(row, 0);
        List<Prestamo> prestamos = prestamoService.listarPrestamos();
        UUID idPrestamo = null;
        for (Prestamo p : prestamos) {
            if (p.getId().toString().startsWith(idTruncado.replace("...", ""))) {
                idPrestamo = p.getId(); break;
            }
        }
        if (idPrestamo == null) return;
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Confirmar devolución del libro?", "Devolución", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String resultado = prestamoService.devolverLibro(idPrestamo);
            if (resultado.equals("OK")) {
                cargarPrestamosTabla(); showSuccess("Devolución realizada correctamente");
            } else { showError(resultado); }
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  PANEL RESERVAS
    // ══════════════════════════════════════════════════════════════════════════
    private JPanel crearPanelReservas() {
        JPanel panel = panelBase();

        tableModelReservas = new DefaultTableModel(
            new String[]{"ID Reserva", "Usuario", "Libro", "Fecha", "Estado"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tableReservas = new JTable(tableModelReservas);
        configurarTabla(tableReservas);
        panel.add(crearScrollPane(tableReservas, "Reservas Activas y Completadas"), BorderLayout.CENTER);

        JButton btnReservar   = crearBoton("🔖  Reservar Libro",   COLOR_BTN_BLUE);
        JButton btnCancelar   = crearBoton("✕  Cancelar Reserva",  COLOR_BTN_RED);
        JButton btnActualizar = crearBoton("↺  Actualizar",         COLOR_BTN_GRAY);
        panel.add(crearPanelBotones(btnReservar, btnCancelar, btnActualizar), BorderLayout.SOUTH);

        btnReservar.addActionListener(e   -> reservarLibroMejorado());
        btnCancelar.addActionListener(e   -> cancelarReserva());
        btnActualizar.addActionListener(e -> cargarReservasTabla());

        cargarReservasTabla();
        return panel;
    }

    private void cargarReservasTabla() {
        tableModelReservas.setRowCount(0);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (Reserva r : reservaService.listarReservas()) {
            Usuario u = usuarioService.buscarPorId(r.getIdUsuario());
            Libro   l = libroService.buscarPorId(r.getIdLibro());
            tableModelReservas.addRow(new Object[]{
                r.getId().toString().substring(0, 8) + "...",
                u != null ? u.getNombre() : r.getIdUsuario().toString().substring(0, 8),
                l != null ? l.getTitulo() : "Libro eliminado",
                r.getFechaReserva().format(dtf),
                r.getEstado()
            });
        }
    }

    private void reservarLibroMejorado() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        List<Libro>   libros   = libroService.listarLibros();
        if (usuarios.isEmpty() || libros.isEmpty()) {
            showWarning("Debe haber usuarios y libros para reservar"); return;
        }
        JComboBox<UsuarioComboItem> comboUsuarios = styledCombo();
        for (Usuario u : usuarios) comboUsuarios.addItem(new UsuarioComboItem(u));
        JComboBox<LibroComboItem> comboLibros = styledCombo();
        for (Libro l : libros) comboLibros.addItem(new LibroComboItem(l));

        JPanel p = new JPanel(new GridLayout(2, 2, 12, 12));
        p.setBorder(new EmptyBorder(16, 16, 16, 16));
        p.setBackground(COLOR_BG_CARD);
        p.add(styledLabel("👤 Usuario:")); p.add(comboUsuarios);
        p.add(styledLabel("📚 Libro:"));   p.add(comboLibros);

        int option = JOptionPane.showConfirmDialog(this, p, "Reservar Libro",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            UsuarioComboItem uItem = (UsuarioComboItem) comboUsuarios.getSelectedItem();
            LibroComboItem   lItem = (LibroComboItem)   comboLibros.getSelectedItem();
            String resultado = reservaService.reservarLibro(uItem.getUsuario().getId(), lItem.getLibro().getId());
            if (resultado.startsWith("OK|")) {
                cargarReservasTabla();
                showSuccess("Reserva registrada con ID: " + resultado.split("\\|")[1].substring(0, 8) + "...");
            } else { showError(resultado); }
        }
    }

    private void cancelarReserva() {
        int row = tableReservas.getSelectedRow();
        if (row == -1) { showWarning("Seleccione una reserva"); return; }
        String idTruncado = (String) tableModelReservas.getValueAt(row, 0);
        List<Reserva> reservas = reservaService.listarReservas();
        UUID idReserva = null;
        for (Reserva r : reservas) {
            if (r.getId().toString().startsWith(idTruncado.replace("...", ""))) {
                idReserva = r.getId(); break;
            }
        }
        if (idReserva == null) return;
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Cancelar esta reserva?", "Cancelar reserva", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String resultado = reservaService.cancelarReserva(idReserva);
            if (resultado.equals("OK")) {
                cargarReservasTabla(); showSuccess("Reserva cancelada");
            } else { showError(resultado); }
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  PANEL MULTAS
    // ══════════════════════════════════════════════════════════════════════════
    private JPanel crearPanelMultas() {
        JPanel panel = panelBase();

        DefaultTableModel modelMultas = new DefaultTableModel(
            new String[]{"Usuario", "Monto Pendiente (€)"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tableMultas = new JTable(modelMultas);
        configurarTabla(tableMultas);
        panel.add(crearScrollPane(tableMultas, "Usuarios con Multas"), BorderLayout.CENTER);

        JButton btnPagar      = crearBoton("💰  Pagar Multa",  COLOR_BTN_YELLOW);
        JButton btnActualizar = crearBoton("↺  Actualizar",    COLOR_BTN_GRAY);
        panel.add(crearPanelBotones(btnPagar, btnActualizar), BorderLayout.SOUTH);

        btnPagar.addActionListener(e      -> pagarMulta(modelMultas));
        btnActualizar.addActionListener(e -> cargarMultas(modelMultas));

        cargarMultas(modelMultas);
        return panel;
    }

    private void cargarMultas(DefaultTableModel model) {
        model.setRowCount(0);
        for (Usuario u : usuarioService.listarUsuarios()) {
            double monto = multaService.obtenerMulta(u.getId());
            if (monto > 0) {
                model.addRow(new Object[]{
                    u.getNombre() + "  (" + u.getId().toString().substring(0, 8) + "...)",
                    String.format("%.2f", monto)
                });
            }
        }
    }

    private void pagarMulta(DefaultTableModel model) {
        String idStr = JOptionPane.showInputDialog(this, "Ingrese el ID del usuario (o parte inicial):");
        if (idStr == null || idStr.trim().isEmpty()) return;
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        UUID idUsuario = null;
        for (Usuario u : usuarios) {
            if (u.getId().toString().startsWith(idStr.trim())) { idUsuario = u.getId(); break; }
        }
        if (idUsuario == null) { showError("Usuario no encontrado"); return; }
        double monto = multaService.obtenerMulta(idUsuario);
        if (monto <= 0) { showWarning("El usuario no tiene multas pendientes"); return; }
        int confirm = JOptionPane.showConfirmDialog(this,
            String.format("Multa pendiente: %.2f €\n¿Desea pagar el total?", monto),
            "Pagar Multa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (multaService.pagarMulta(idUsuario)) {
                cargarMultas(model); showSuccess("Multa pagada exitosamente");
            } else { showError("Error al procesar el pago"); }
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  HELPERS DE DIÁLOGO Y COMPONENTES
    // ══════════════════════════════════════════════════════════════════════════

    private int showDialog(Object[] message, String title) {
        return JOptionPane.showConfirmDialog(this, message, title,
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    }

    private void showSuccess(String msg) {
        JOptionPane.showMessageDialog(this, "✅  " + msg, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, "❌  " + msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showWarning(String msg) {
        JOptionPane.showMessageDialog(this, "⚠️  " + msg, "Aviso", JOptionPane.WARNING_MESSAGE);
    }

    private JTextField styledField() {
        JTextField f = new JTextField(22);
        f.setBackground(new Color(35, 43, 65));
        f.setForeground(COLOR_TEXT_PRIMARY);
        f.setCaretColor(COLOR_ACCENT_GOLD);
        f.setFont(FONT_TABLE_CELL);
        f.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(COLOR_BORDER, 1),
            new EmptyBorder(6, 10, 6, 10)));
        return f;
    }

    private JTextField styledField(String text) {
        JTextField f = styledField();
        f.setText(text);
        return f;
    }

    private JLabel styledLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(FONT_BTN);
        l.setForeground(COLOR_TEXT_PRIMARY);
        return l;
    }

    private <T> JComboBox<T> styledCombo() {
        JComboBox<T> cb = new JComboBox<>();
        cb.setBackground(new Color(35, 43, 65));
        cb.setForeground(COLOR_TEXT_PRIMARY);
        cb.setFont(FONT_TABLE_CELL);
        return cb;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  MAIN
    // ══════════════════════════════════════════════════════════════════════════
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Biblioteca());
    }
}