/** 
 * app.js — Biblioteca DDD
 * Lógica de UI y controllers de vista
 * CORREGIDO: Botón cancelar en reservas + normalización de estados
 */

/* =============================================
   TOAST NOTIFICATIONS
   ============================================= */

function showToast(msg, type = 'info') {
  const container = document.getElementById('toastContainer');
  const t = document.createElement('div');
  t.className = `toast toast--${type}`;
  const icons = {
    success: `<svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5"><polyline points="20 6 9 17 4 12"/></svg>`,
    error:   `<svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5"><circle cx="12" cy="12" r="10"/><line x1="15" y1="9" x2="9" y2="15"/><line x1="9" y1="9" x2="15" y2="15"/></svg>`,
    info:    `<svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5"><circle cx="12" cy="12" r="10"/><line x1="12" y1="8" x2="12" y2="12"/><line x1="12" y1="16" x2="12.01" y2="16"/></svg>`
  };
  t.innerHTML = `${icons[type] || icons.info}<span>${msg}</span>`;
  container.appendChild(t);
  setTimeout(() => t.remove(), 4000);
}

/* =============================================
   MODAL MANAGEMENT
   ============================================= */

function openModal(id) {
  document.getElementById(id).classList.remove('hidden');
}

function closeModal(id) {
  document.getElementById(id).classList.add('hidden');
}

document.addEventListener('click', e => {
  const btn = e.target.closest('[data-close]');
  if (btn) closeModal(btn.dataset.close);
  if (e.target.classList.contains('modal-overlay')) {
    e.target.classList.add('hidden');
  }
});

/* =============================================
   NAVIGATION
   ============================================= */

const PAGES = ['dashboard', 'libros', 'usuarios', 'prestamos', 'reservas', 'multas'];
const PAGE_TITLES = {
  dashboard: 'Dashboard',
  libros: 'Catálogo de Libros',
  usuarios: 'Gestión de Usuarios',
  prestamos: 'Préstamos',
  reservas: 'Reservas',
  multas: 'Multas'
};

function navigateTo(page) {
  PAGES.forEach(p => {
    document.getElementById(`page-${p}`).classList.toggle('hidden', p !== page);
  });
  document.querySelectorAll('.nav-item').forEach(el => {
    el.classList.toggle('active', el.dataset.page === page);
  });
  document.getElementById('breadcrumbTitle').textContent = PAGE_TITLES[page] || page;
  if (page === 'dashboard') loadDashboard();
  if (page === 'libros')    loadLibros();
  if (page === 'usuarios')  loadUsuarios();
  if (page === 'prestamos') loadPrestamos();
  if (page === 'reservas')  loadReservas();
  document.getElementById('sidebar').classList.remove('open');
  document.getElementById('sidebarOverlay').classList.remove('show');
}

document.querySelectorAll('.nav-item').forEach(el => {
  el.addEventListener('click', e => {
    e.preventDefault();
    navigateTo(el.dataset.page);
  });
});

document.getElementById('menuBtn').addEventListener('click', () => {
  document.getElementById('sidebar').classList.toggle('open');
  document.getElementById('sidebarOverlay').classList.toggle('show');
});
document.getElementById('sidebarOverlay').addEventListener('click', () => {
  document.getElementById('sidebar').classList.remove('open');
  document.getElementById('sidebarOverlay').classList.remove('show');
});

/* =============================================
   HELPERS
   ============================================= */

function statusBadge(estado) {
  const map = {
    'ACTIVO':     ['badge--blue',  'Activo'],
    'DEVUELTO':   ['badge--green', 'Devuelto'],
    'VENCIDO':    ['badge--rose',  'Vencido'],
    'PENDIENTE':  ['badge--amber', 'Pendiente'],
    'ACTIVA':     ['badge--amber', 'Activa'],
    'CANCELADO':  ['badge--muted', 'Cancelado'],
    'COMPLETADO': ['badge--green', 'Completado'],
  };
  const [cls, label] = map[estado?.toUpperCase()] || ['badge--muted', estado || '—'];
  return `<span class="badge ${cls}">${label}</span>`;
}

function stockIndicator(stock) {
  let dotClass = 'stock-dot--ok';
  if (stock === 0) dotClass = 'stock-dot--zero';
  else if (stock <= 2) dotClass = 'stock-dot--low';
  return `<span class="stock-indicator"><span class="stock-dot ${dotClass}"></span>${stock}</span>`;
}

function fmtDate(d) {
  if (!d) return '—';
  return new Date(d).toLocaleDateString('es-EC', { day: '2-digit', month: 'short', year: 'numeric' });
}

function shortId(id) {
  if (!id) return '—';
  return id.length > 12 ? id.substring(0, 12) + '…' : id;
}

/* =============================================
   DASHBOARD
   ============================================= */

async function loadDashboard() {
  try {
    const libros = await LibrosAPI.listar();
    document.getElementById('stat-libros').textContent = libros.length;
    const tbody = document.getElementById('dashRecentLibros');
    tbody.innerHTML = libros.slice(0, 6).map(l => `
      <div class="mini-row">
        <div>
          <div class="mini-row-label">${l.titulo}</div>
          <div class="mini-row-sub">${l.autor} · ${l.codigo}</div>
        </div>
        ${stockIndicator(l.stock)}
      </div>
    `).join('') || '<div class="loading-row">Sin libros registrados</div>';
  } catch(e) { document.getElementById('stat-libros').textContent = '—'; }
  try {
    const usuarios = await UsuariosAPI.listar();
    document.getElementById('stat-usuarios').textContent = usuarios.length;
  } catch(e) { document.getElementById('stat-usuarios').textContent = '—'; }
  try {
    const prestamos = await PrestamosAPI.listarTodos();
    const activos = prestamos.filter(p => p.estado === 'ACTIVO' || p.estado === 'activo');
    document.getElementById('stat-prestamos').textContent = activos.length;
    const tbody = document.getElementById('dashRecentPrestamos');
    tbody.innerHTML = prestamos.slice(0, 6).map(p => `
      <div class="mini-row">
        <div>
          <div class="mini-row-label">${p.libroCodigo}</div>
          <div class="mini-row-sub">Usuario: ${shortId(p.usuarioId)} · ${fmtDate(p.fechaLimite)}</div>
        </div>
        ${statusBadge(p.estado)}
      </div>
    `).join('') || '<div class="loading-row">Sin préstamos registrados</div>';
  } catch(e) {
    document.getElementById('stat-prestamos').textContent = '—';
    document.getElementById('dashRecentPrestamos').innerHTML = '<div class="loading-row">No disponible</div>';
  }
  try {
    const reservas = await ReservasAPI.listarTodas();
    const activas = reservas.filter(r => ['PENDIENTE','ACTIVA'].includes(r.estado?.toUpperCase()));
    document.getElementById('stat-reservas').textContent = activas.length;
  } catch(e) { document.getElementById('stat-reservas').textContent = '—'; }
}

/* =============================================
   LIBROS
   ============================================= */

let _libros = [];
async function loadLibros() {
  const tbody = document.getElementById('tbodyLibros');
  tbody.innerHTML = `<tr><td colspan="5" class="loading-cell">Cargando…</td></tr>`;
  try {
    _libros = await LibrosAPI.listar();
    renderLibros(_libros);
  } catch(e) { tbody.innerHTML = `<tr><td colspan="5" class="loading-cell">Error: ${e.message}</td></tr>`; }
}
function renderLibros(list) {
  const tbody = document.getElementById('tbodyLibros');
  if (!list.length) { tbody.innerHTML = `<tr><td colspan="5" class="loading-cell">No hay libros registrados</td></tr>`; return; }
  tbody.innerHTML = list.map(l => `
    <tr>
      <td><code style="background:var(--bg-overlay);padding:2px 8px;border-radius:4px;font-size:0.8rem">${l.codigo}</code></td>
      <td><strong>${l.titulo}</strong></td>
      <td style="color:var(--text-secondary)">${l.autor}</td>
      <td>${stockIndicator(l.stock)}</td>
      <td><div class="action-btns">
        <button class="btn-icon" title="Editar" onclick="editLibro('${l.codigo}')">
          <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"/><path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"/></svg>
        </button>
        <button class="btn-icon btn-icon--danger" title="Eliminar" onclick="confirmDeleteLibro('${l.codigo}')">
          <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><polyline points="3 6 5 6 21 6"/><path d="M19 6l-1 14H6L5 6"/><path d="M10 11v6"/><path d="M14 11v6"/><path d="M9 6V4h6v2"/></svg>
        </button>
      </div></td>
    </tr>
  `).join('');
}
document.getElementById('searchLibros').addEventListener('input', e => {
  const q = e.target.value.toLowerCase();
  renderLibros(_libros.filter(l => l.titulo.toLowerCase().includes(q) || l.autor.toLowerCase().includes(q) || l.codigo.toLowerCase().includes(q)));
});
document.getElementById('btnNuevoLibro').addEventListener('click', () => {
  document.getElementById('modalLibroTitle').textContent = 'Registrar Libro';
  document.getElementById('libroEditCodigo').value = '';
  document.getElementById('libroCodigo').disabled = false;
  document.getElementById('libroCodigo').value = '';
  document.getElementById('libroTitulo').value = '';
  document.getElementById('libroAutor').value = '';
  document.getElementById('libroStock').value = '';
  openModal('modalLibro');
});
function editLibro(codigo) {
  const libro = _libros.find(l => l.codigo === codigo);
  if (!libro) return;
  document.getElementById('modalLibroTitle').textContent = 'Editar Libro';
  document.getElementById('libroEditCodigo').value = codigo;
  document.getElementById('libroCodigo').value = libro.codigo;
  document.getElementById('libroCodigo').disabled = true;
  document.getElementById('libroTitulo').value = libro.titulo;
  document.getElementById('libroAutor').value = libro.autor;
  document.getElementById('libroStock').value = libro.stock;
  openModal('modalLibro');
}
document.getElementById('btnGuardarLibro').addEventListener('click', async () => {
  const editCodigo = document.getElementById('libroEditCodigo').value;
  const payload = {
    codigo:  document.getElementById('libroCodigo').value.trim(),
    titulo:  document.getElementById('libroTitulo').value.trim(),
    autor:   document.getElementById('libroAutor').value.trim(),
    stock:   parseInt(document.getElementById('libroStock').value, 10)
  };
  if (!payload.titulo || !payload.autor || isNaN(payload.stock)) { showToast('Completa todos los campos obligatorios', 'error'); return; }
  try {
    if (editCodigo) {
      await LibrosAPI.editar(editCodigo, { titulo: payload.titulo, autor: payload.autor, stock: payload.stock });
      showToast('Libro actualizado correctamente', 'success');
    } else {
      if (!payload.codigo) { showToast('El código es obligatorio', 'error'); return; }
      await LibrosAPI.registrar(payload);
      showToast('Libro registrado correctamente', 'success');
    }
    closeModal('modalLibro');
    loadLibros();
  } catch(e) { showToast(e.message, 'error'); }
});
let _deleteLibroCodigo = null;
function confirmDeleteLibro(codigo) {
  _deleteLibroCodigo = codigo;
  document.getElementById('modalConfirmMsg').textContent = `¿Eliminar el libro con código "${codigo}"? Esta acción no se puede deshacer.`;
  openModal('modalConfirm');
}
document.getElementById('btnConfirmDelete').addEventListener('click', async () => {
  if (_deleteLibroCodigo) {
    try {
      await LibrosAPI.eliminar(_deleteLibroCodigo);
      showToast('Libro eliminado', 'success');
      closeModal('modalConfirm');
      loadLibros();
    } catch(e) { showToast(e.message, 'error'); }
    _deleteLibroCodigo = null;
  }
});

/* =============================================
   USUARIOS
   ============================================= */

let _usuarios = [];
async function loadUsuarios() {
  const tbody = document.getElementById('tbodyUsuarios');
  tbody.innerHTML = `<tr><td colspan="3" class="loading-cell">Cargando…</td></tr>`;
  try {
    _usuarios = await UsuariosAPI.listar();
    renderUsuarios(_usuarios);
  } catch(e) { tbody.innerHTML = `<tr><td colspan="3" class="loading-cell">Error: ${e.message}</td></tr>`; }
}
function renderUsuarios(list) {
  const tbody = document.getElementById('tbodyUsuarios');
  if (!list.length) { tbody.innerHTML = `<tr><td colspan="3" class="loading-cell">No hay usuarios registrados</td></tr>`; return; }
  tbody.innerHTML = list.map(u => `
    <tr>
      <td><code style="background:var(--bg-overlay);padding:2px 8px;border-radius:4px;font-size:0.8rem">${u.id}</code></td>
      <td><strong>${u.nombre}</strong></td>
      <td><div class="action-btns">
        <button class="btn-icon" title="Editar" onclick="editUsuario('${u.id}')">
          <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"/><path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"/></svg>
        </button>
        <button class="btn-icon btn-icon--danger" title="Eliminar" onclick="confirmDeleteUsuario('${u.id}')">
          <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><polyline points="3 6 5 6 21 6"/><path d="M19 6l-1 14H6L5 6"/><path d="M10 11v6"/><path d="M14 11v6"/><path d="M9 6V4h6v2"/></svg>
        </button>
      </div></td>
    </tr>
  `).join('');
}
document.getElementById('searchUsuarios').addEventListener('input', e => {
  const q = e.target.value.toLowerCase();
  renderUsuarios(_usuarios.filter(u => u.nombre.toLowerCase().includes(q) || u.id.toLowerCase().includes(q)));
});
document.getElementById('btnNuevoUsuario').addEventListener('click', () => {
  document.getElementById('modalUsuarioTitle').textContent = 'Registrar Usuario';
  document.getElementById('usuarioEditId').value = '';
  document.getElementById('usuarioId').disabled = false;
  document.getElementById('usuarioId').value = '';
  document.getElementById('usuarioNombre').value = '';
  openModal('modalUsuario');
});
function editUsuario(id) {
  const u = _usuarios.find(u => u.id === id);
  if (!u) return;
  document.getElementById('modalUsuarioTitle').textContent = 'Editar Usuario';
  document.getElementById('usuarioEditId').value = id;
  document.getElementById('usuarioId').value = u.id;
  document.getElementById('usuarioId').disabled = true;
  document.getElementById('usuarioNombre').value = u.nombre;
  openModal('modalUsuario');
}
document.getElementById('btnGuardarUsuario').addEventListener('click', async () => {
  const editId = document.getElementById('usuarioEditId').value;
  const id     = document.getElementById('usuarioId').value.trim();
  const nombre = document.getElementById('usuarioNombre').value.trim();
  if (!nombre) { showToast('El nombre es obligatorio', 'error'); return; }
  try {
    if (editId) {
      await UsuariosAPI.editar(editId, { nombre });
      showToast('Usuario actualizado', 'success');
    } else {
      if (!id) { showToast('El ID es obligatorio', 'error'); return; }
      await UsuariosAPI.registrar({ id, nombre });
      showToast('Usuario registrado', 'success');
    }
    closeModal('modalUsuario');
    loadUsuarios();
  } catch(e) { showToast(e.message, 'error'); }
});
let _deleteUsuarioId = null;
function confirmDeleteUsuario(id) {
  _deleteUsuarioId = id;
  document.getElementById('modalConfirmMsg').textContent = `¿Eliminar el usuario "${id}"?`;
  openModal('modalConfirm');
}

/* =============================================
   PRÉSTAMOS
   ============================================= */

let _prestamos = [];
async function loadPrestamos(usuarioId = null) {
  const tbody = document.getElementById('tbodyPrestamos');
  tbody.innerHTML = `<tr><td colspan="7" class="loading-cell">Cargando…</td></tr>`;
  try {
    _prestamos = usuarioId ? await PrestamosAPI.listarPorUsuario(usuarioId) : await PrestamosAPI.listarTodos();
    renderPrestamos(_prestamos);
  } catch(e) { tbody.innerHTML = `<tr><td colspan="7" class="loading-cell">Error: ${e.message}</td></tr>`; }
}
function renderPrestamos(list) {
  const tbody = document.getElementById('tbodyPrestamos');
  if (!list.length) { tbody.innerHTML = `<tr><td colspan="7" class="loading-cell">No hay préstamos</td></tr>`; return; }
  tbody.innerHTML = list.map(p => `
    <tr>
      <td><code style="font-size:0.78rem;background:var(--bg-overlay);padding:2px 7px;border-radius:4px">${shortId(p.id)}</code></td>
      <td style="color:var(--text-secondary)">${p.usuarioId}</td>
      <td><strong>${p.libroCodigo}</strong></td>
      <td>${statusBadge(p.estado)}</td>
      <td style="color:var(--text-secondary);font-size:0.83rem">${fmtDate(p.fechaLimite)}</td>
      <td>${p.multaGenerada != null && p.multaGenerada > 0 ? `<span style="color:var(--rose);font-weight:600">$${p.multaGenerada.toFixed(2)}</span>` : '<span style="color:var(--text-muted)">—</span>'}</td>
      <td><div class="action-btns">
        ${(p.estado === 'ACTIVO' || p.estado === 'activo') ? `
          <button class="btn-icon btn-icon--success" title="Registrar devolución" onclick="openDevolucion('${p.id}','${p.usuarioId}')">
            <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M9 14L4 9l5-5"/><path d="M20 20v-7a4 4 0 0 0-4-4H4"/></svg>
          </button>
        ` : ''}
      </div></td>
    </tr>
  `).join('');
}
document.getElementById('btnFiltrarPrestamos').addEventListener('click', () => {
  const uid = document.getElementById('filterPrestamosUsuario').value.trim();
  if (uid) loadPrestamos(uid);
});
document.getElementById('btnTodosPrestamos').addEventListener('click', () => {
  document.getElementById('filterPrestamosUsuario').value = '';
  loadPrestamos();
});
document.getElementById('btnNuevoPrestamo').addEventListener('click', async () => {
  resetSearchableSelect('prestamoUsuario');
  resetSearchableSelect('prestamoLibro');
  openModal('modalPrestamo');
  await Promise.all([ loadSearchableUsers('prestamoUsuario'), loadSearchableLibros('prestamoLibro') ]);
});
document.getElementById('btnGuardarPrestamo').addEventListener('click', async () => {
  const usuarioId   = document.getElementById('prestamoUsuarioId').value.trim();
  const libroCodigo = document.getElementById('prestamoLibroCodigo').value.trim();
  if (!usuarioId || !libroCodigo) { showToast('Selecciona un usuario y un libro', 'error'); return; }
  try {
    await PrestamosAPI.prestar({ usuarioId, libroCodigo });
    showToast('Préstamo registrado correctamente', 'success');
    closeModal('modalPrestamo');
    loadPrestamos();
  } catch(e) { showToast(e.message, 'error'); }
});
function openDevolucion(prestamoId, usuarioId) {
  document.getElementById('devolucionPrestamoId').value = prestamoId;
  document.getElementById('devolucionUsuarioId').value = usuarioId;
  openModal('modalDevolucion');
}
document.getElementById('btnConfirmarDevolucion').addEventListener('click', async () => {
  const prestamoId = document.getElementById('devolucionPrestamoId').value;
  const usuarioId  = document.getElementById('devolucionUsuarioId').value;
  try {
    const result = await PrestamosAPI.devolver({ prestamoId, usuarioId });
    let msg = 'Devolución registrada correctamente';
    if (result && result.multaGenerada && result.multaGenerada > 0) msg += `. Se generó una multa de $${result.multaGenerada.toFixed(2)}`;
    showToast(msg, 'success');
    closeModal('modalDevolucion');
    loadPrestamos();
  } catch(e) { showToast(e.message, 'error'); }
});

/* =============================================
   RESERVAS (CORREGIDO: botón cancelar para ACTIVA)
   ============================================= */

let _reservas = [];
async function loadReservas(usuarioId = null) {
  const tbody = document.getElementById('tbodyReservas');
  tbody.innerHTML = `<tr><td colspan="6" class="loading-cell">Cargando…</td></tr>`;
  try {
    _reservas = usuarioId ? await ReservasAPI.listarPorUsuario(usuarioId) : await ReservasAPI.listarTodas();
    renderReservas(_reservas);
  } catch(e) { tbody.innerHTML = `<tr><td colspan="6" class="loading-cell">Error: ${e.message}</td></tr>`; }
}
function renderReservas(list) {
  const tbody = document.getElementById('tbodyReservas');
  if (!list.length) { tbody.innerHTML = `<tr><td colspan="6" class="loading-cell">No hay reservas</td></tr>`; return; }
  tbody.innerHTML = list.map(r => {
    const estadoUp = (r.estado || '').toUpperCase();
    const isCancelable = estadoUp === 'PENDIENTE' || estadoUp === 'ACTIVA';
    return `
    <tr>
      <td><code style="font-size:0.78rem;background:var(--bg-overlay);padding:2px 7px;border-radius:4px">${shortId(r.id)}</code></td>
      <td style="color:var(--text-secondary)">${r.usuarioId}</td>
      <td><strong>${r.libroCodigo}</strong></td>
      <td>${statusBadge(r.estado)}</td>
      <td style="color:var(--text-secondary);font-size:0.83rem">${fmtDate(r.fechaReserva)}</td>
      <td><div class="action-btns">
        ${isCancelable ? `
          <button class="btn-icon btn-icon--danger" title="Cancelar reserva" onclick="openCancelarReserva('${r.id}','${r.usuarioId}')">
            <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="12" cy="12" r="10"/><line x1="15" y1="9" x2="9" y2="15"/><line x1="9" y1="9" x2="15" y2="15"/></svg>
          </button>
        ` : ''}
      </div></td>
    </tr>
  `}).join('');
}
document.getElementById('btnFiltrarReservas').addEventListener('click', () => {
  const uid = document.getElementById('filterReservasUsuario').value.trim();
  if (uid) loadReservas(uid);
});
document.getElementById('btnTodasReservas').addEventListener('click', () => {
  document.getElementById('filterReservasUsuario').value = '';
  loadReservas();
});
document.getElementById('btnNuevaReserva').addEventListener('click', async () => {
  resetSearchableSelect('reservaUsuario');
  resetSearchableSelect('reservaLibro');
  openModal('modalReserva');
  await Promise.all([ loadSearchableUsers('reservaUsuario'), loadSearchableLibros('reservaLibro') ]);
});
document.getElementById('btnGuardarReserva').addEventListener('click', async () => {
  const usuarioId   = document.getElementById('reservaUsuarioId').value.trim();
  const libroCodigo = document.getElementById('reservaLibroCodigo').value.trim();
  if (!usuarioId || !libroCodigo) { showToast('Selecciona un usuario y un libro', 'error'); return; }
  try {
    await ReservasAPI.reservar({ usuarioId, libroCodigo });
    showToast('Reserva creada correctamente', 'success');
    closeModal('modalReserva');
    loadReservas();
  } catch(e) { showToast(e.message, 'error'); }
});
function openCancelarReserva(reservaId, usuarioId) {
  document.getElementById('cancelarReservaId').value = reservaId;
  document.getElementById('cancelarReservaUsuarioId').value = usuarioId;
  openModal('modalCancelarReserva');
}
document.getElementById('btnConfirmarCancelarReserva').addEventListener('click', async () => {
  const reservaId = document.getElementById('cancelarReservaId').value;
  const usuarioId = document.getElementById('cancelarReservaUsuarioId').value;
  try {
    await ReservasAPI.cancelar({ reservaId, usuarioId });
    showToast('Reserva cancelada', 'success');
    closeModal('modalCancelarReserva');
    loadReservas();
  } catch(e) { showToast(e.message, 'error'); }
});

/* =============================================
   MULTAS
   ============================================= */

document.getElementById('btnConsultarMulta').addEventListener('click', async () => {
  const usuarioId = document.getElementById('multaUsuarioId').value.trim();
  if (!usuarioId) { showToast('Ingresa el ID del usuario', 'error'); return; }
  const resultDiv = document.getElementById('multaResult');
  resultDiv.classList.remove('hidden', 'has-multa', 'no-multa');
  try {
    const data = await MultasAPI.consultarPorUsuario(usuarioId);
    resultDiv.classList.remove('hidden');
    if (data.tieneMultas) {
      resultDiv.classList.add('has-multa');
      resultDiv.innerHTML = `⚠️ El usuario <strong>${usuarioId}</strong> tiene multas pendientes.`;
    } else {
      resultDiv.classList.add('no-multa');
      resultDiv.innerHTML = `✔ El usuario <strong>${usuarioId}</strong> no tiene multas pendientes.`;
    }
  } catch(e) { resultDiv.textContent = `Error: ${e.message}`; showToast(e.message, 'error'); }
});
document.getElementById('btnPagarMulta').addEventListener('click', async () => {
  const prestamoId = document.getElementById('multaPrestamoId').value.trim();
  const usuarioId  = document.getElementById('multaPagarUsuarioId').value.trim();
  if (!prestamoId || !usuarioId) { showToast('Completa el ID de préstamo y usuario', 'error'); return; }
  const resultDiv = document.getElementById('multaPagoResult');
  resultDiv.classList.remove('hidden', 'has-multa', 'no-multa', 'success');
  try {
    const data = await MultasAPI.pagarPorPrestamo({ prestamoId, usuarioId });
    resultDiv.classList.add('success');
    resultDiv.innerHTML = `✔ Multa pagada correctamente. Monto pendiente restante: <strong>$${(data.montoPendiente || 0).toFixed(2)}</strong>`;
    showToast('Multa pagada correctamente', 'success');
  } catch(e) { resultDiv.textContent = `Error: ${e.message}`; showToast(e.message, 'error'); }
});

/* =============================================
   SEARCHABLE SELECT ENGINE
   ============================================= */

function createSearchableSelect(prefix, { hiddenId, items, labelFn, subFn, valueFn }) {
  const searchEl    = document.getElementById(`${prefix}Search`);
  const dropdownEl  = document.getElementById(`${prefix}Dropdown`);
  const hiddenEl    = document.getElementById(hiddenId);
  const controlEl   = document.getElementById(`${prefix}Control`);
  let focusIdx = -1;
  let filtered  = items;
  function highlight(text, query) {
    if (!query) return text;
    const escaped = query.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
    return text.replace(new RegExp(`(${escaped})`, 'gi'), '<mark class="ss-hl">$1</mark>');
  }
  function render(query = '') {
    const q = query.toLowerCase().trim();
    filtered = q ? items.filter(it => labelFn(it).toLowerCase().includes(q) || (subFn && subFn(it) || '').toLowerCase().includes(q) || valueFn(it).toLowerCase().includes(q)) : items;
    focusIdx = -1;
    if (!filtered.length) dropdownEl.innerHTML = `<div class="ss-empty">Sin resultados para "${query}"</div>`;
    else {
      dropdownEl.innerHTML = filtered.map((it, i) => {
        const label = highlight(labelFn(it), query);
        const sub   = subFn ? highlight(subFn(it) || '', query) : '';
        return `<div class="ss-option" data-idx="${i}" data-value="${valueFn(it)}">
          <span class="ss-option-main">${label}</span>
          ${sub ? `<span class="ss-option-sub">${sub}</span>` : ''}
        </div>`;
      }).join('');
      dropdownEl.querySelectorAll('.ss-option').forEach(opt => {
        opt.addEventListener('mousedown', e => { e.preventDefault(); selectItem(filtered[parseInt(opt.dataset.idx)]); });
      });
    }
    dropdownEl.classList.remove('hidden');
    controlEl.classList.add('open');
  }
  function selectItem(item) {
    hiddenEl.value     = valueFn(item);
    searchEl.value     = labelFn(item);
    searchEl.dataset.selected = 'true';
    dropdownEl.classList.add('hidden');
    controlEl.classList.remove('open');
  }
  function close() { dropdownEl.classList.add('hidden'); controlEl.classList.remove('open'); focusIdx = -1; }
  searchEl.addEventListener('focus', () => { if (searchEl.dataset.selected === 'true') searchEl.select(); render(searchEl.value); });
  searchEl.addEventListener('input', () => { hiddenEl.value = ''; searchEl.dataset.selected = 'false'; render(searchEl.value); });
  searchEl.addEventListener('keydown', e => {
    const opts = dropdownEl.querySelectorAll('.ss-option');
    if (e.key === 'ArrowDown') { e.preventDefault(); focusIdx = Math.min(focusIdx + 1, opts.length - 1); }
    else if (e.key === 'ArrowUp') { e.preventDefault(); focusIdx = Math.max(focusIdx - 1, 0); }
    else if (e.key === 'Enter' && focusIdx >= 0) { e.preventDefault(); selectItem(filtered[focusIdx]); return; }
    else if (e.key === 'Escape') { close(); return; }
    opts.forEach((o, i) => o.classList.toggle('focused', i === focusIdx));
    if (focusIdx >= 0) opts[focusIdx].scrollIntoView({ block: 'nearest' });
  });
  searchEl.addEventListener('blur', () => setTimeout(close, 150));
}
async function loadSearchableUsers(prefix) {
  const dropdown = document.getElementById(`${prefix}Dropdown`);
  dropdown.innerHTML = `<div class="ss-loading"><span class="ss-spinner"></span>Cargando usuarios…</div>`;
  dropdown.classList.remove('hidden');
  let users;
  try { users = (_usuarios && _usuarios.length) ? _usuarios : await UsuariosAPI.listar(); }
  catch(e) { dropdown.innerHTML = `<div class="ss-empty">Error al cargar usuarios</div>`; return; }
  const hiddenId = prefix === 'prestamoUsuario' ? 'prestamoUsuarioId' : 'reservaUsuarioId';
  createSearchableSelect(prefix, { hiddenId, items: users, labelFn: u => u.nombre, subFn: u => `ID: ${u.id}`, valueFn: u => u.id });
  document.getElementById(`${prefix}Search`).dispatchEvent(new Event('focus'));
}
async function loadSearchableLibros(prefix) {
  const dropdown = document.getElementById(`${prefix}Dropdown`);
  dropdown.innerHTML = `<div class="ss-loading"><span class="ss-spinner"></span>Cargando libros…</div>`;
  dropdown.classList.remove('hidden');
  let libros;
  try { libros = (_libros && _libros.length) ? _libros : await LibrosAPI.listar(); }
  catch(e) { dropdown.innerHTML = `<div class="ss-empty">Error al cargar libros</div>`; return; }
  const hiddenId = prefix === 'prestamoLibro' ? 'prestamoLibroCodigo' : 'reservaLibroCodigo';
  createSearchableSelect(prefix, {
    hiddenId, items: libros,
    labelFn: l => l.titulo,
    subFn: l => {
      let stockLabel = '';
      if (l.stock === 0)      stockLabel = `<span class="ss-badge-stock zero">Sin stock</span>`;
      else if (l.stock <= 2)  stockLabel = `<span class="ss-badge-stock low">${l.stock} disponibles</span>`;
      else                    stockLabel = `<span class="ss-badge-stock ok">${l.stock} disponibles</span>`;
      return `${l.codigo} · ${l.autor}${stockLabel}`;
    },
    valueFn: l => l.codigo
  });
  //document.getElementById(`${prefix}Search`).dispatchEvent(new Event('focus'));
}
function resetSearchableSelect(prefix) {
  const search   = document.getElementById(`${prefix}Search`);
  const dropdown = document.getElementById(`${prefix}Dropdown`);
  const control  = document.getElementById(`${prefix}Control`);
  const hiddenIdMap = {
    prestamoUsuario: 'prestamoUsuarioId',
    prestamoLibro:   'prestamoLibroCodigo',
    reservaUsuario:  'reservaUsuarioId',
    reservaLibro:    'reservaLibroCodigo'
  };
  const hidden = document.getElementById(hiddenIdMap[prefix]);
  if (search)   { search.value = ''; search.dataset.selected = 'false'; }
  if (dropdown) { dropdown.innerHTML = ''; dropdown.classList.add('hidden'); }
  if (control)  { control.classList.remove('open'); }
  if (hidden)   { hidden.value = ''; }
}

/* =============================================
   INIT & SESSION
   ============================================= */

loadDashboard();
(function initSession() {
  const raw = sessionStorage.getItem('bib_session');
  if (!raw) { window.location.replace('login.html'); return; }
  try {
    const session = JSON.parse(raw);
    const el = document.getElementById('topbarUser');
    if (el && session.user) el.textContent = '👤 ' + session.user;
  } catch(_) {}
})();
document.getElementById('btnLogout').addEventListener('click', () => {
  sessionStorage.removeItem('bib_session');
  window.location.replace('login.html');
});