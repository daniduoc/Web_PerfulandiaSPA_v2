const API_URL = `http://${window.location.hostname}:8080/api/v1/perfumes`;

function listarPerfumes() {
  fetch(API_URL)
    .then((response) => response.json())
    .then((perfumes) => {
      const container = document.getElementById("productos-container");
      container.innerHTML = "";
      perfumes.forEach((perfume) => {
        container.innerHTML += `
                    <div class="col">
                        <div class="card h-100 border-0">
                            <div style="height: 180px; display: flex; align-items: center; justify-content: center; background: white; padding: 10px;">
                                <img src="${
                                  perfume.imagenUrl
                                }" style="max-height: 100%; max-width: 100%; object-fit: contain; "alt="${
          perfume.nombre
        }">
                            </div>
                            <div class="card-body" style="padding: 12px; text-align: center;">
                                <h5 class="card-title" style="font-size: 1rem; margin-bottom: 8px;">${
                                  perfume.nombre
                                }</h5>
                                <p class="card-text text-success fw-bold" style="margin-bottom: 4px;">
                                    $${perfume.precio.toLocaleString()}
                                </p>
                                <p class="text-muted small" style="margin-bottom: 8px;">
                                    Stock: ${perfume.stock} unidades
                                </p>
                                <button class="btn w-100" onclick="carrito.agregarAlCarrito(${
                                  perfume.id
                                })">
                                    🛒 Agregar
                                </button>
                            </div>
                        </div>         
                    </div>
                `;
      });
    });
}
listarPerfumes();
