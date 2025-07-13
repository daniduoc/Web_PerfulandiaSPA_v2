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

//Importar assembler para HATEOAS
import com.example.PerfulandiaSPA.assemblers.UsuarioModelAssembler;

//iMPORTAR LAS CLASES NECESARIAS PARA HATEOAS
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

//importar las clases para mejorar los modelos de respuesta
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;

//import las clases de ResponseEntity para manejar las respuestas http
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/v2/usuarios")
@CrossOrigin
@Tag(name = "Usuario", description = "Operaciones sobre los usuarios de controller")
public class UsuarioControllerV2 {

    @Autowired
    private UsuarioService serv;

    @Autowired
    private UsuarioModelAssembler assembler;

    @Operation(summary = "Registrar usuario", description = "Añadir un usuario en la tabla de usuarios")
    @PostMapping(value = "/registrar", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Usuario>> registrar(@RequestBody Usuario u) {
        Usuario creado = serv.registrar(u);
        return ResponseEntity
                .created(linkTo(methodOn(UsuarioControllerV2.class).registrar(creado)).toUri())
                .body(assembler.toModel(creado));
    }

    @Operation(summary = "Iniciar sesion", description = "Autentica un usuario con email y contraseña")
    @PostMapping(value = "/login", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Map<String, String>>> login(@RequestBody Usuario u) {
        Optional<Usuario> user = serv.autenticar(u.getEmail(), u.getPassword());
        Map<String, String> response = new HashMap<>();
        if (user.isPresent()) {
            response.put("result", "OK");
            response.put("nombre", user.get().getNombre());
            response.put("email", user.get().getEmail());
            response.put("password", user.get().getPassword());
        } else {
            response.put("result", "Error");
        }

        EntityModel<Map<String, String>> model = EntityModel.of(response);
        model.add(linkTo(methodOn(UsuarioControllerV2.class).login(u)).withSelfRel());
        model.add(linkTo(methodOn(UsuarioControllerV2.class).registrar(new Usuario())).withRel("registrar"));

        return ResponseEntity.ok(model);
    }
}