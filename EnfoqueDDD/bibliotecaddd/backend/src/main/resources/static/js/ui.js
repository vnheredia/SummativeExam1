// js/ui.js
function mostrarModal(titulo, contenidoHtml, onSubmit) {
    const modal = document.getElementById('modal');
    const modalBody = document.getElementById('modal-body');
    modalBody.innerHTML = `<h3 style="margin-bottom: 1rem; color: var(--primary);">${titulo}</h3><form id="modal-form">${contenidoHtml}<div style="margin-top:1.5rem"><button type="submit" class="btn-primary" style="width:100%">Guardar</button></div></form>`;
    modal.style.display = 'flex';
    const form = document.getElementById('modal-form');
    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        const submitBtn = form.querySelector('button[type="submit"]');
        const originalText = submitBtn.innerHTML;
        submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Guardando...';
        submitBtn.disabled = true;
        try {
            await onSubmit(e);
            modal.style.display = 'none';
            if (typeof recargarTabActual === 'function') recargarTabActual();
        } catch (error) {
            showToast(error.message, 'error');
        } finally {
            submitBtn.innerHTML = originalText;
            submitBtn.disabled = false;
        }
    });
    document.querySelector('.close').onclick = () => modal.style.display = 'none';
}