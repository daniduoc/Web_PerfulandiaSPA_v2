function registrar() {
    const nombre = document.getElementById("nombre").value
    const email = document.getElementById("email").value
    const password = document.getElementById("password").value

    if (nombre.length < 3){
        alert("Nombre demasiado corto");
        return;
    }
    if (!email.includes('@')) {
        alert("Correo no válido, no incluye '@'");
        return;
    }
    if (!email.includes('.')) {
        alert("Correo no válido. no incluye '.'")
        return;
    }
    if (password.length < 8) {
        alert("Contraseña demasiado corta")
        return;
    }
    fetch("http://localhost:8080/api/v1/usuarios/registrar",{
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({
            nombre: document.getElementById("nombre").value,
            email: document.getElementById("email").value,
            password: document.getElementById("password").value
        })
    })
    .then(res => res.json())
    .then(data => alert("Usuario registrado"));
    window.location.href = "/index.html"; // Se carga el index
}
