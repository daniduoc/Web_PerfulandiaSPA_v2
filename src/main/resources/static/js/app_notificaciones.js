const notificaciones = (() => {
  const API_NOTIFICACIONES = `http://${window.location.hostname}:8080/api/v1/notificaciones`;
  let notificacionesNoLeidas = 0;

  async function cargarNotificaciones() {
    try {
        // Obtener solo las no leídas para el contador
        const responseNoLeidas = await fetch(`${API_NOTIFICACIONES}/noleidas`);
        const noLeidas = await responseNoLeidas.json();
        actualizarContador(noLeidas.length);
        
        // Obtener todas para mostrar en la lista
        const responseTodas = await fetch(API_NOTIFICACIONES);
        const todas = await responseTodas.json();
        actualizarListaNotificaciones(todas);
    } catch (err) {
        console.error("Error al cargar notificaciones", err);
    }
}

  function actualizarContador(cantidad) {
    notificacionesNoLeidas = cantidad;
    const counter = document.getElementById("notificationCounter");
    counter.textContent = cantidad;
    counter.classList.toggle("d-none", cantidad === 0);
  }

  function actualizarListaNotificaciones(notificaciones) {
    const lista = document.getElementById("notificationsList");
    lista.innerHTML = "";

    if (notificaciones.length === 0) {
      lista.innerHTML = `
                <li class="notification-item text-center py-3">
                    <p class="text-muted mb-0">No hay notificaciones nuevas</p>
                </li>
            `;
      return;
    }

    notificaciones.forEach((notificacion) => {
      const item = document.createElement("li");
      item.className = `notification-item px-3 py-2 ${
        notificacion.leida ? "" : "bg-light"
      }`;
      item.innerHTML = `
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <strong>${notificacion.titulo}</strong>
                        <p class="mb-0 small">${notificacion.mensaje}</p>
                    </div>
                    <small class="text-muted">${new Date(
                      notificacion.fecha
                    ).toLocaleTimeString()}</small>
                </div>
            `;
      item.addEventListener("click", () => marcarComoLeida(notificacion.id));
      lista.appendChild(item);
    });
  }

  async function marcarComoLeida(id) {
    try {
        await fetch(`${API_NOTIFICACIONES}/leer/${id}`, {
            method: "POST",
        });
        // Vuelve a cargar las notificaciones para actualizar el contador
        await cargarNotificaciones();
    } catch (err) {
        console.error("Error al marcar notificación como leída", err);
    }
}

  async function marcarTodasComoLeidas() {
    try {
        // Actualización optimista
        notificacionesNoLeidas = 0;
        actualizarContador(notificacionesNoLeidas);
        
        await fetch(`${API_NOTIFICACIONES}/leer/todas`, {
            method: "POST",
        });
        
        // Actualiza la lista completa
        await cargarNotificaciones();
    } catch (err) {
        console.error("Error al marcar notificaciones como leídas", err);
        await cargarNotificaciones();
    }
}

  // Event listeners
  document.getElementById("markAllAsRead")?.addEventListener("click", (e) => {
    e.preventDefault();
    marcarTodasComoLeidas();
  });

  // Inicialización
  document.addEventListener("DOMContentLoaded", () => {
    cargarNotificaciones();
    // Actualizar cada 5 segundos
    setInterval(cargarNotificaciones, 5000);
  });

  return {
    cargarNotificaciones,
    marcarComoLeida,
    marcarTodasComoLeidas,
  };
})();
