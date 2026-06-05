// js/users.js
const usersService = {
    getAll: () => apiClient.get('/usuarios'),
    create: (data) => apiClient.post('/usuarios', data),
    update: (id, data) => apiClient.put(`/usuarios/${id}`, data),
    delete: (id) => apiClient.delete(`/usuarios/${id}`),
    getMultas: (usuarioId) => apiClient.get(`/usuarios/multas/${usuarioId}`).catch(() => ({ tieneMultas: false, monto: 0 }))
};

async function cargarUsuarios() {
    const tbody = document.getElementById('tbody-usuarios');
    tbody.innerHTML = '<tr><td colspan="3" class="loading">Cargando...</td></tr>';
    try {
        const usuarios = await usersService.getAll();
        if (usuarios.length === 0) {
            tbody.innerHTML = '<tr><td colspan="3">No hay usuarios registrados</td></tr>';
            return;
        }
        tbody.innerHTML = usuarios.map(u => `
            <tr>
                <td>${escapeHtml(u.id)}</td>
                <td>${escapeHtml(u.nombre)}</td>
                <td>
                    <button class="btn-warning" onclick="editarUsuario('${u.id}')"><i class="fas fa-edit"></i></button>
                    <button class="btn-danger" onclick="eliminarUsuario('${u.id}')"><i class="fas fa-trash"></i></button>
                </td>
            </tr>
        `).join('');
    } catch(e) {
        tbody.innerHTML = `<tr><td colspan="3">Error: ${e.message}</td></tr>`;
    }
}

window.editarUsuario = (id) => {
    mostrarModal('Editar Usuario', `
        <div class="form-group"><label>Nombre</label><input type="text" name="nombre" required></div>
    `, async (e) => {
        const nombre = e.target.nombre.value;
        await usersService.update(id, { nombre });
        showMessage('Usuario actualizado');
        cargarUsuarios();
    });
};

window.eliminarUsuario = async (id) => {
    if (confirm('¿Eliminar usuario? Se comprobarán préstamos activos.')) {
        await usersService.delete(id);
        showMessage('Usuario eliminado');
        cargarUsuarios();
    }
};

document.getElementById('btn-nuevo-usuario').onclick = () => {
    mostrarModal('Nuevo Usuario', `
        <div class="form-group"><label>ID (5-20 alfanumérico)</label><input type="text" name="id" pattern="[A-Za-z0-9-]{5,20}" required></div>
        <div class="form-group"><label>Nombre</label><input type="text" name="nombre" required></div>
    `, async (e) => {
        await usersService.create({ id: e.target.id.value, nombre: e.target.nombre.value });
        showMessage('Usuario registrado');
        cargarUsuarios();
    });
};