// js/auth.js – Login simulado (mock) para el frontend
// Credenciales de prueba: admin / admin

document.addEventListener('DOMContentLoaded', function() {
    const loginOverlay = document.getElementById('login-overlay');
    const loginForm = document.getElementById('login-form');

    // Si ya se autenticó en esta sesión (sessionStorage), ocultamos el overlay directamente
    if (sessionStorage.getItem('auth') === 'true') {
        if (loginOverlay) loginOverlay.style.display = 'none';
        return;
    }

    if (loginForm) {
        loginForm.addEventListener('submit', function(e) {
            e.preventDefault();
            const username = document.getElementById('username').value.trim();
            const password = document.getElementById('password').value.trim();

            // Simulación de validación (credenciales fijas)
            if (username === 'admin' && password === 'admin') {
                // Guardar en sessionStorage para no volver a mostrar el login (opcional)
                sessionStorage.setItem('auth', 'true');
                // Ocultar el overlay con animación
                loginOverlay.style.opacity = '0';
                setTimeout(() => {
                    loginOverlay.style.display = 'none';
                }, 400);
            } else {
                alert('Credenciales incorrectas. Prueba con admin / admin');
            }
        });
    } else {
        console.warn('No se encontró el formulario de login');
    }
});