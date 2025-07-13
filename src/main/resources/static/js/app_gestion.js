// Este archivo contiene el código JavaScript para la gestión de perfumes en la aplicación web.
const API_URL = "http://localhost:8080/api/v1/perfumes"; // URL de la API para acceder a los perfumes

// Función para listar los perfumes en la tabla
function listarPerfumes() {
    fetch(API_URL)
        .then(response => response.json())
        .then(perfumes => {
            const tbody = document.querySelector("#tablaPerfumes tbody");
            tbody.innerHTML = "";
            perfumes.forEach(perfume => {
                const fila = `
                    <tr>
                        <td>${perfume.id}</td>
                        <td>${perfume.nombre}</td>
                        <td>${perfume.marca}</td>
                        <td>${perfume.isbn}</td>
                        <td>$${perfume.precio.toLocaleString()}</td>
                        <td>${perfume.descripcion}</td>
                        <td> 
                            <button class="btn btn-success btn-sm" onclick="carrito.agregarAlCarrito(${perfume.id})">🛒 Agregar</button>
                        </td>
                    </tr>
                `;
                tbody.innerHTML += fila;
            });
        });
}

// Función para agregar un perfume
function agregarPerfume() {
    const nombre = document.getElementById("nombre").value;
    const marca = document.getElementById("marca").value;
    const isbn = document.getElementById("isbn").value;
    const precio = document.getElementById("precio").value;
    const descripcion = document.getElementById("descripcion").value;
    
    const nuevoPerfume = {
        nombre,
        marca,
        isbn,
        precio: parseInt(precio) || 0,
        descripcion
    };

    fetch(API_URL, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(nuevoPerfume)
    })
    .then(response => response.json())
    .then(data => {
        alert("Perfume agregado exitosamente");
        listarPerfumes();
        limpiarFormulario();
    });
}

// Función para eliminar un perfume
function eliminarPerfume(id) {
    fetch(`${API_URL}/${id}`, { method: "DELETE" })
        .then(response => {
            if (response.ok) {
                alert("Perfume eliminado exitosamente");
                listarPerfumes();
            }
        });
}

// Variables globales
let perfumes = [];
let perfumeEnEdicionId = null;

// Función para buscar un perfume por su ID
function buscarPerfume(id) {
    fetch(`${API_URL}/${id}`)
        .then(response => response.json())
        .then(perfume => {
            document.getElementById("nombre").value = perfume.nombre;
            document.getElementById("marca").value = perfume.marca;
            document.getElementById("isbn").value = perfume.isbn;
            document.getElementById("precio").value = perfume.precio;
            document.getElementById("descripcion").value = perfume.descripcion;

            perfumeEnEdicionId = perfume.id;
            
            const boton = document.getElementById("botonFormulario");
            if (boton) {
                boton.textContent = "Actualizar Perfume";
                boton.onclick = function() {
                    actualizarPerfume(perfume.id);
                };
            }
        });
}

// Función para actualizar un perfume
function actualizarPerfume(id) {
    const nombre = document.getElementById("nombre").value;
    const marca = document.getElementById("marca").value;
    const isbn = document.getElementById("isbn").value;
    const precio = document.getElementById("precio").value;
    const descripcion = document.getElementById("descripcion").value;

    const perfumeActualizado = {
        id: id,
        nombre: nombre,
        marca: marca,
        isbn: isbn,
        precio: parseInt(precio) || 0,
        descripcion: descripcion
    };

    fetch(`${API_URL}/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(perfumeActualizado)
    })
    .then(response => response.json())
    .then(data => {
        alert("Perfume actualizado exitosamente");
        listarPerfumes();
        limpiarFormulario();
    });
}

// Función para limpiar el formulario
function limpiarFormulario() {
    document.getElementById("nombre").value = "";
    document.getElementById("marca").value = "";
    document.getElementById("isbn").value = "";
    document.getElementById("precio").value = "";
    document.getElementById("descripcion").value = "";

    const boton = document.getElementById("botonFormulario");
    boton.innerText = "Agregar Perfume";
    boton.setAttribute("onclick", "agregarPerfume()");

    perfumeEnEdicionId = null;
}

// Cargar perfumes al iniciar
document.addEventListener('DOMContentLoaded', listarPerfumes);