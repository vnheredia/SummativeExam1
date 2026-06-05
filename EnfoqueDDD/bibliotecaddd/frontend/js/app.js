// js/app.js
let currentTab = 'usuarios';

function recargarTabActual() {
    switch(currentTab) {
        case 'usuarios': cargarUsuarios(); break;
        case 'libros': cargarLibros(); break;
        case 'prestamos': cargarPrestamos(); break;
        case 'reservas': cargarReservas(); break;
        default: break;
    }
}

// Navegación por pestañas
document.querySelectorAll('.nav-item').forEach(btn => {
    btn.addEventListener('click', () => {
        document.querySelectorAll('.nav-item').forEach(b => b.classList.remove('active'));
        btn.classList.add('active');
        const tabId = btn.getAttribute('data-tab');
        currentTab = tabId;
        document.querySelectorAll('.tab-pane').forEach(pane => pane.classList.remove('active'));
        document.getElementById(`tab-${tabId}`).classList.add('active');
        if (tabId === 'usuarios') cargarUsuarios();
        else if (tabId === 'libros') cargarLibros();
        else if (tabId === 'prestamos') cargarPrestamos();
        else if (tabId === 'reservas') cargarReservas();
        else if (tabId === 'multas') {
            document.getElementById('usuario-multa').value = '';
            document.getElementById('info-multa').style.display = 'none';
            document.getElementById('btn-pagar-multa').style.display = 'none';
        }
    });
});

// Carga inicial
cargarUsuarios();