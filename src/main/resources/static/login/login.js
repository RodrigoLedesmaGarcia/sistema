async function login() {
    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value.trim();
    const message = document.getElementById("message");
    const loginBtn = document.getElementById("loginBtn");
    const btnText = document.getElementById("btnText");
    const btnSpinner = document.getElementById("btnSpinner");

    // Oculta mensajes previos
    message.style.display = 'none';
    message.className = 'alert';

    if (!username || !password) {
        showMessage('Por favor, completa ambos campos.', 'danger');
        return;
    }

    // Cambia estado del botón
    loginBtn.disabled = true;
    btnText.textContent = 'Verificando...';
    btnSpinner.style.display = 'inline-block';

    try {
        // Enviar usuario y contraseña normales (no en Base64)
        const response = await fetch("/api/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ username, password })
        });

        const data = await response.json();

        if (response.ok && data.success) {
            showMessage(`${data.message}`, 'success');

            // Redirigir al formulario de clientes
            setTimeout(() => {
                window.location.href = data.redirect || "/clientes/nuevo";
            }, 1200);
        } else {
            showMessage(`${data.message || "Credenciales inválidas"}`, 'danger');
        }

    } catch (error) {
        console.error('❌ Error:', error);
        showMessage("Error al conectar con el servidor.", 'danger');
    } finally {
        // Restaurar botón
        loginBtn.disabled = false;
        btnText.textContent = 'Acceder';
        btnSpinner.style.display = 'none';
    }
}

function showMessage(text, type) {
    const message = document.getElementById("message");
    message.textContent = text;
    message.className = `alert alert-${type}`;
    message.style.display = 'block';
}

document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('input').forEach(input => {
        input.addEventListener('keypress', e => {
            if (e.key === 'Enter') login();
        });
    });
});
