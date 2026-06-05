// js/catalog.js
const catalogService = {
    getAll: () => apiClient.get('/libros'),
    create: (data) => apiClient.post('/libros', data),
    update: (codigo, data) => apiClient.put(`/libros/${codigo}`, data),
    delete: (codigo) => apiClient.delete(`/libros/${codigo}`)
};

async function cargarLibros() {
    const tbody = document.getElementById('tbody-libros');
    tbody.innerHTML = '<tr><td colspan="5" class="loading">Cargando...</td></tr>';
    try {
        const libros = await catalogService.getAll();
        if (libros.length === 0) {
            tbody.innerHTML = '<tr><td colspan="5">No hay libros registrados</td></tr>';
            return;
        }
        tbody.innerHTML = libros.map(l => `
            <tr>
                <td>${escapeHtml(l.codigo)}</td>
                <td>${escapeHtml(l.titulo)}</td>
                <td>${escapeHtml(l.autor)}</td>
                <td>${l.stock}</td>
                <td>
                    <button class="btn-warning" onclick="editarLibro('${l.codigo}')"><i class="fas fa-edit"></i></button>
                    <button class="btn-danger" onclick="eliminarLibro('${l.codigo}')"><i class="fas fa-trash"></i></button>
                </td>
            </tr>
        `).join('');
    } catch(e) {
        tbody.innerHTML = `<tr><td colspan="5">Error: ${e.message}</td></tr>`;
    }
}

window.editarLibro = (codigo) => {
    mostrarModal('Editar Libro', `
        <div class="form-group"><label>Título</label><input type="text" name="titulo" required></div>
        <div class="form-group"><label>Autor</label><input type="text" name="autor" required></div>
        <div class="form-group"><label>Stock</label><input type="number" name="stock" min="0" required></div>
    `, async (e) => {
        const titulo = e.target.titulo.value;
        const autor = e.target.autor.value;
        const stock = parseInt(e.target.stock.value);
        await catalogService.update(codigo, { titulo, autor, stock });
        showMessage('Libro actualizado');
        cargarLibros();
    });
};

window.eliminarLibro = async (codigo) => {
    if (confirm('¿Eliminar libro? Se comprobarán préstamos y reservas activos.')) {
        await catalogService.delete(codigo);
        showMessage('Libro eliminado');
        cargarLibros();
    }
};

document.getElementById('btn-nuevo-libro').onclick = () => {
    mostrarModal('Nuevo Libro', `
        <div class="form-group"><label>Código (3-10 mayúsculas/números)</label><input type="text" name="codigo" pattern="[A-Z0-9]{3,10}" required></div>
        <div class="form-group"><label>Título</label><input type="text" name="titulo" required></div>
        <div class="form-group"><label>Autor</label><input type="text" name="autor" required></div>
        <div class="form-group"><label>Stock inicial</label><input type="number" name="stock" min="1" required></div>
    `, async (e) => {
        await catalogService.create({
            codigo: e.target.codigo.value,
            titulo: e.target.titulo.value,
            autor: e.target.autor.value,
            stock: parseInt(e.target.stock.value)
        });
        showMessage('Libro registrado');
        cargarLibros();
    });
};