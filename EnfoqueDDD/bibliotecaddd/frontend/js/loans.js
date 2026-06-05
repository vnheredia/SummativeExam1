// js/loans.js
const loansService = {
    getPrestamos: () => apiClient.get('/prestamos/prestamos'),
    crearPrestamo: (data) => apiClient.post('/prestamos/prestar', data),
    devolver: (prestamoId) => apiClient.put('/prestamos/devolver', { prestamoId }),
    getReservas: () => apiClient.get('/prestamos/reservas'),
    crearReserva: (data) => apiClient.post('/prestamos/reservar', data),
    cancelarReserva: (reservaId) => apiClient.put('/prestamos/cancelar-reserva', { reservaId }),
    pagarMulta: (usuarioId) => apiClient.post('/prestamos/pagar-multas', { usuarioId })
};

async function cargarPrestamos() {
    const tbody = document.getElementById('tbody-prestamos');
    tbody.innerHTML = '<tr><td colspan="8" class="loading">Cargando...</td></tr>';
    try {
        const prestamos = await loansService.getPrestamos();
        if (prestamos.length === 0) {
            tbody.innerHTML = '<tr><td colspan="8">No hay préstamos registrados</td></tr>';
            return;
        }
        tbody.innerHTML = prestamos.map(p => `
            <tr>
                <td>${escapeHtml(p.id)}</td>
                <td>${escapeHtml(p.usuarioId)}</td>
                <td>${escapeHtml(p.libroCodigo)}</td>
                <td>${p.estado}</td>
                <td>${p.fechaPrestamo}</td>
                <td>${p.fechaLimite}</td>
                <td>${p.multaGenerada > 0 ? p.multaGenerada + '€' : '-'}</td>
                <td>${p.estado === 'ACTIVO' ? `<button class="btn-success" onclick="devolverLibro('${p.id}')">Devolver</button>` : 'Devuelto'}</td>
            </tr>
        `).join('');
    } catch(e) {
        tbody.innerHTML = `<tr><td colspan="8">Error: ${e.message}</td></tr>`;
    }
}

async function cargarReservas() {
    const tbody = document.getElementById('tbody-reservas');
    tbody.innerHTML = '<tr><td colspan="6" class="loading">Cargando...</td></tr>';
    try {
        const reservas = await loansService.getReservas();
        if (reservas.length === 0) {
            tbody.innerHTML = '<tr><td colspan="6">No hay reservas activas</td></tr>';
            return;
        }
        tbody.innerHTML = reservas.map(r => `
            <tr>
                <td>${escapeHtml(r.id)}</td>
                <td>${escapeHtml(r.usuarioId)}</td>
                <td>${escapeHtml(r.libroCodigo)}</td>
                <td>${r.fechaReserva}</td>
                <td>${r.estado}</td>
                <td>${r.estado === 'ACTIVA' ? `<button class="btn-danger" onclick="cancelarReserva('${r.id}')">Cancelar</button>` : '-'}</td>
            </tr>
        `).join('');
    } catch(e) {
        tbody.innerHTML = `<tr><td colspan="6">Error: ${e.message}</td></tr>`;
    }
}

window.devolverLibro = async (prestamoId) => {
    if (confirm('¿Registrar devolución?')) {
        await loansService.devolver(prestamoId);
        showMessage('Devolución registrada');
        cargarPrestamos();
    }
};

window.cancelarReserva = async (reservaId) => {
    if (confirm('¿Cancelar reserva?')) {
        await loansService.cancelarReserva(reservaId);
        showMessage('Reserva cancelada');
        cargarReservas();
    }
};

document.getElementById('btn-nuevo-prestamo').onclick = async () => {
    const [usuarios, libros] = await Promise.all([usersService.getAll(), catalogService.getAll()]);
    const usuarioOptions = usuarios.map(u => `<option value="${u.id}">${u.id} - ${u.nombre}</option>`).join('');
    const libroOptions = libros.filter(l => l.stock > 0).map(l => `<option value="${l.codigo}">${l.codigo} - ${l.titulo}</option>`).join('');
    mostrarModal('Nuevo Préstamo', `
        <div class="form-group"><label>Usuario</label><select name="usuarioId">${usuarioOptions}</select></div>
        <div class="form-group"><label>Libro (con stock disponible)</label><select name="libroCodigo">${libroOptions}</select></div>
    `, async (e) => {
        await loansService.crearPrestamo({
            usuarioId: e.target.usuarioId.value,
            libroCodigo: e.target.libroCodigo.value
        });
        showMessage('Préstamo registrado');
        cargarPrestamos();
    });
};

document.getElementById('btn-nueva-reserva').onclick = async () => {
    const [usuarios, libros] = await Promise.all([usersService.getAll(), catalogService.getAll()]);
    const usuarioOptions = usuarios.map(u => `<option value="${u.id}">${u.id} - ${u.nombre}</option>`).join('');
    const libroOptions = libros.map(l => `<option value="${l.codigo}">${l.codigo} - ${l.titulo}</option>`).join('');
    mostrarModal('Nueva Reserva', `
        <div class="form-group"><label>Usuario</label><select name="usuarioId">${usuarioOptions}</select></div>
        <div class="form-group"><label>Libro</label><select name="libroCodigo">${libroOptions}</select></div>
    `, async (e) => {
        await loansService.crearReserva({
            usuarioId: e.target.usuarioId.value,
            libroCodigo: e.target.libroCodigo.value
        });
        showMessage('Reserva registrada');
        cargarReservas();
    });
};

// Multas
document.getElementById('btn-consultar-multa').onclick = async () => {
    const usuarioId = document.getElementById('usuario-multa').value.trim();
    if (!usuarioId) return alert('Ingrese ID de usuario');
    try {
        const info = await usersService.getMultas(usuarioId);
        const div = document.getElementById('info-multa');
        if (info.tieneMultas || (info.monto && info.monto > 0)) {
            const monto = info.monto || 5.0;
            div.innerHTML = `<strong>Multa pendiente:</strong> ${monto}€`;
            div.style.display = 'block';
            const btnPagar = document.getElementById('btn-pagar-multa');
            btnPagar.style.display = 'inline-block';
            btnPagar.onclick = async () => {
                await loansService.pagarMulta(usuarioId);
                showMessage('Multa pagada con éxito');
                div.style.display = 'none';
                btnPagar.style.display = 'none';
                document.getElementById('usuario-multa').value = '';
            };
        } else {
            div.innerHTML = '<strong>El usuario no tiene multas pendientes.</strong>';
            div.style.display = 'block';
            document.getElementById('btn-pagar-multa').style.display = 'none';
        }
    } catch(e) {
        alert('Error al consultar: ' + e.message);
    }
};