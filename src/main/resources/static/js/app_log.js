API_URL = `http://${window.location.hostname}:8080/api/v1/usuarios/login`;
// Funcion  o metodo para autenticar el usuario registrado en la base de datos
function login(){
    fetch(API_URL,{
        method: "POST",
        headers: {"Content-Type":"application/json"},
        body: JSON.stringify({
            email: document.getElementById("email").value, // Valida el nombre igresado en el login con el de la base de datos
            password: document.getElementById("password").value // Valida la password ingresado en el login con la base de datos
        })
    })
    .then(Response => Response.json())
    .then(data => { // Manejamos la respuesta del servidor 
        if (data.result === "OK") { // Si la validacion es correcta 
            sessionStorage.setItem("nombreUsuario", data.nombre); // Se muestra el nombre
            window.location.href = "/index.html"; // Se carga el index
        } else { // Si la validacion es incorrecta
            alert("Acceso ha sido denegado."); // se niega el acceso
        }
    });
}