package com.example.PerfulandiaSPA.Controller;

import com.example.PerfulandiaSPA.Model.Usuario;
import com.example.PerfulandiaSPA.Service.UsuarioService;

import java.util.Optional;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/usuarios") //URL base para las definiciones HTTP
@CrossOrigin //Permitir ediciones desde cualquier origen
@Tag(name = "Usuario", description = "Operaciones sobre los usuarios de controller")
public class UsuarioController {
    @Autowired
    private UsuarioService serv;

    @Operation(summary = "Registrar usuario", description = "Añadir un usuario en la tabla de usuarios")
    @PostMapping("/registrar") // Este metodo es el metodo para registrar un usuario en la tabla usuario
    public Usuario registrar(@RequestBody Usuario u) {    
        return serv.registrar(u);
    }

    @Operation(summary = "Iniciar sesion", description = "Autentica un usuario con email y contraseña")
    // Metodo para autenticar usuario 
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Usuario u) {
        Optional<Usuario> user = serv.autenticar(u.getEmail(), u.getPassword());
        Map<String, String> response = new HashMap<>();
        if(user.isPresent()) {
            response.put("result","OK");
            response.put("nombre",user.get().getNombre());
            response.put("password",user.get().getPassword());
        } else {
            response.put("result","Error");
        }
        return response;
    }
}
