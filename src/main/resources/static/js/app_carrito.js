const carrito = (() => {
  const API_CARRITO = "/api/v1/carrito";
  const API_PERFUMES = "http://localhost:8080/api/v1/perfumes";

  async function listarCarrito() {
    try {
      const response = await fetch(API_CARRITO);
      const perfumes = await response.json();
      const TotalPerfumes = document.getElementById("totalCarrito");
      const TotalPrecio = document.getElementById("precioTotal");
      const listaCarrito = document.getElementById("listaCarrito");
      listaCarrito.innerHTML = "";
      TotalPerfumes.textContent = perfumes.length;

      let total = 0;
      perfumes.forEach((perfume) => {
        total += perfume.precio;
        listaCarrito.innerHTML += `
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        <span class="flex-grow-1">${perfume.nombre}</span>
                        <span class="fw-bold mx-3">$${perfume.precio.toLocaleString()}</span>
                        <button class="btn btn-sm" onclick="carrito.eliminarPerfume(${
                          perfume.id
                        })">
                            🗑️
                        </button>
                    </li>
                `;
      });
      TotalPrecio.textContent = `$${total.toLocaleString()}`;
    } catch (err) {
      console.error("Error al cargar el carrito", err);
    }
  }

  async function agregarAlCarrito(id) {
     // Verificar stock primero
     const disponible = await fetch(`/api/v1/stock/${id}/disponible/1`)
         .then(res => res.json());
    
     if (!disponible) {
         alert("No hay stock suficiente!");
         return;
     }

     try {
         const response = await fetch(`${API_CARRITO}/agregar/${id}`, {method: "POST"});
         const mensaje = await response.text();
         alert(mensaje);
         listarCarrito();
     } catch (err) {
         alert("No se pudo agregar el perfume al carrito");
     }
 }

  async function eliminarPerfume(id) {
    try {
      const response = await fetch(`${API_CARRITO}/eliminar/${id}`, {
        method: "DELETE",
      });
      const mensaje = await response.text();
      alert(mensaje);
      listarCarrito();
    } catch (err) {
      alert("No se pudo eliminar el perfume del carrito");
    }
  }

  async function vaciarCarrito() {
    const total = document.getElementById("totalCarrito").textContent;
    if (parseInt(total) == 0) {
      alert("El carrito está vacío.");
      return;
    }
    if (confirm("¿Estás seguro de vaciar el carrito?")) {
      try {
        const response = await fetch(`${API_CARRITO}/vaciar`, {
          method: "DELETE",
        });
        const mensaje = await response.text();
        alert(mensaje);
        listarCarrito();
      } catch (err) {
        console.error("Error al vaciar el carrito", err);
      }
    }
  }

 async function confirmarCompra() {
    const total = document.getElementById("totalCarrito").textContent;
    if (parseInt(total) == 0) {
        alert("El carrito está vacío.");
        return;
    }
    
    if (confirm(`¿Confirmar compra de ${total} perfumes?`)) {
        try {
            const response = await fetch(API_CARRITO);
            const perfumes = await response.json();
            
            // Actualizar stock para cada producto único
            const productosUnicos = [...new Set(perfumes.map(p => p.id))];
            for (const id of productosUnicos) {
                const cantidad = perfumes.filter(p => p.id === id).length;
                await fetch(`/api/v1/stock/${id}/reducir/${cantidad}`, {
                    method: "PUT"
                });
            }

           await fetch(`/api/v1/notificaciones/crear?titulo=Compra realizada&mensaje=Has comprado ${total} perfumes. ¡Gracias por tu compra!`, {
                  method: 'POST'
            });
            
            alert("¡Gracias por tu compra!");
            await fetch(`${API_CARRITO}/vaciar`, {method: "DELETE"});
            listarCarrito();
            listarPerfumes();
        } catch (err) {
            console.error("Error al confirmar compra", err);
            alert("Ocurrió un error al procesar la compra");
        }
    }
}


  return {
    listarCarrito,
    agregarAlCarrito,
    eliminarPerfume,
    vaciarCarrito,
    confirmarCompra,
  };
})();