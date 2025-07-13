const nombre = sessionStorage.getItem("nombreUsuario");
if (nombre) {
    document.getElementById("btn_login").style.display = "none";
    document.getElementById("btn_registro").textContent = "Cerrar Sesión";
    document.getElementById("btn_registro").href = "/index.html"
    if (document.getElementById("mensaje")) {
        document.getElementById("mensaje").textContent = `Bienvenid@, ${nombre}`;
    }
    document.getElementById("btn_registro").onclick = function() {
        sessionStorage.clear();
        window.location.href = "/index.html"
    }
}