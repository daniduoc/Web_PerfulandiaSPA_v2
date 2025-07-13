document.getElementById("formIncidencia").addEventListener("submit", async function (e) {
    e.preventDefault();

    const datos = {
        nombreUsuario: document.getElementById("nombreUsuario").value,
        correo: document.getElementById("correo").value,
        asunto: document.getElementById("asunto").value,
        descripcion: document.getElementById("descripcion").value
    };

    try {
        const response = await fetch("http://localhost:8080/api/v1/incidencias", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(datos)
        });

        if (response.ok) {
            alert("Incidencia reportada exitosamente.");
            document.getElementById("formIncidencia").reset();
        } else {
            alert("Error al enviar la incidencia.");
        }
    } catch (err) {
        console.error("Error:", err);
        alert("Error de conexión con el servidor.");
    }
});
