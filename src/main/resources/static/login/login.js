async function login() {
    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value.trim();
    const message = document.getElementById("message");
    const loginBtn = document.getElementById("loginBtn");
    const btnText = document.getElementById("btnText");
    const btnSpinner = document.getElementById("btnSpinner");

    message.style.display = 'none';
    message.className = 'alert';

    if (!username || !password) {
        showMessage('Por favor, completa ambos campos.', 'danger');
        return;
    }

    loginBtn.disabled = true;
    btnText.textContent = 'Verificando...';
    btnSpinner.style.display = 'inline-block';

    try {
        const response = await fetch("/api/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ username, password })
        });

        const data = await response.json();

        if (response.ok && data.success) {
            // Guardar token JWT
            localStorage.setItem("token", data.token);

            showMessage(`${data.message}`, 'success');

            // Redirigir después de un breve delay
            setTimeout(() => {
                window.location.href = data.redirect || "/clientes/nuevo";
            }, 1000);
        } else {
            showMessage(data.message || "Credenciales inválidas", 'danger');
        }

    } catch (error) {
        console.error('❌ Error:', error);
        showMessage("Error al conectar con el servidor.", 'danger');
    } finally {
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
