// js/ui.js
function mostrarModal(titulo, contenidoHtml, onSubmit) {
    const modal = document.getElementById('modal');
    const modalBody = document.getElementById('modal-body');
    modalBody.innerHTML = `<h3>${titulo}</h3><form id="modal-form">${contenidoHtml}<div style="margin-top:1rem"><button type="submit" class="btn-primary">Guardar</button></div></form>`;
    modal.style.display = 'flex';
    document.getElementById('modal-form').addEventListener('submit', async (e) => {
        e.preventDefault();
        await onSubmit(e);
        modal.style.display = 'none';
    });
    document.querySelector('.close').onclick = () => modal.style.display = 'none';
}

function showMessage(msg, isError = false) {
    alert(isError ? `❌ ${msg}` : `✅ ${msg}`);
}

function escapeHtml(str) {
    if (!str) return '';
    return str.replace(/[&<>]/g, function(m) {
        if (m === '&') return '&amp;';
        if (m === '<') return '&lt;';
        if (m === '>') return '&gt;';
        return m;
    });
}