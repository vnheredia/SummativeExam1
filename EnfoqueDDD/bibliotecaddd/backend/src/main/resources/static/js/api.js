/**
 * api.js — Biblioteca DDD
 * Capa de servicios para comunicación con el backend Spring Boot
 * Base URL: /api
 */

const API_BASE = '/api';

async function request(method, url, body = null) {
  const opts = {
    method,
    headers: { 'Content-Type': 'application/json' }
  };
  if (body) opts.body = JSON.stringify(body);

  const res = await fetch(API_BASE + url, opts);

  if (res.status === 204) return null; // No Content

  const text = await res.text();
  let data = null;
  try { data = JSON.parse(text); } catch (_) { data = text; }

  if (!res.ok) {
    const msg = (data && (data.message || data.error)) || `Error ${res.status}`;
    throw new Error(msg);
  }

  return data;
}

/* =============================================
   LIBROS  —  /api/libros
   ============================================= */

const LibrosAPI = {
  listar: () => request('GET', '/libros'),
  buscar: (codigo) => request('GET', `/libros/${encodeURIComponent(codigo)}`),
  registrar: (data) => request('POST', '/libros', data),
  editar: (codigo, data) => request('PUT', `/libros/${encodeURIComponent(codigo)}`, data),
  eliminar: (codigo) => request('DELETE', `/libros/${encodeURIComponent(codigo)}`)
};

/* =============================================
   USUARIOS  —  /api/usuarios
   ============================================= */

const UsuariosAPI = {
  listar: () => request('GET', '/usuarios'),
  buscar: (id) => request('GET', `/usuarios/${encodeURIComponent(id)}`),
  registrar: (data) => request('POST', '/usuarios', data),
  editar: (id, data) => request('PUT', `/usuarios/${encodeURIComponent(id)}`, data),
  eliminar: (id) => request('DELETE', `/usuarios/${encodeURIComponent(id)}`)
};

/* =============================================
   PRÉSTAMOS  —  /api/prestamos
   ============================================= */

const PrestamosAPI = {
  listarTodos: () => request('GET', '/prestamos/prestamos'),
  listarPorUsuario: (usuarioId) => request('GET', `/prestamos/prestamos?usuarioId=${encodeURIComponent(usuarioId)}`),
  prestar: (data) => request('POST', '/prestamos/prestar', data),
  devolver: (data) => request('PUT', '/prestamos/devolver', data),
};

/* =============================================
   RESERVAS  —  /api/prestamos/reservas
   ============================================= */

const ReservasAPI = {
  listarTodas: () => request('GET', '/prestamos/reservas'),
  listarPorUsuario: (usuarioId) => request('GET', `/prestamos/reservas?usuarioId=${encodeURIComponent(usuarioId)}`),
  reservar: (data) => request('POST', '/prestamos/reservar', data),
  cancelar: (data) => request('PUT', '/prestamos/cancelar-reserva', data),
};

/* =============================================
   MULTAS  —  /api/usuarios/multas & /api/prestamos/pagar-multas
   ============================================= */

const MultasAPI = {
  consultarPorUsuario: (usuarioId) => request('GET', `/usuarios/multas/${encodeURIComponent(usuarioId)}`),
  pagarPorUsuario: (usuarioId) => request('POST', `/usuarios/multas/${encodeURIComponent(usuarioId)}/pagar`),
  pagarPorPrestamo: (data) => request('POST', '/prestamos/pagar-multas', data),
};
