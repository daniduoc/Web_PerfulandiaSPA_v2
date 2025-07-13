package com.example.PerfulandiaSPA.assemblers;

//Importar las clases necesarias para el Modelo y Controller
import com.example.PerfulandiaSPA.Model.Usuario;
import com.example.PerfulandiaSPA.Controller.UsuarioControllerV2;

//Importar la clase static para crear los enlaces HATEOAS
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

//Importar la clase EntityModel para usar los HATEOAS
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

//Importar la anotacion NonNull para  los metodos no acepten valores nulos
import org.springframework.lang.NonNull;

//Agregar la anotacion @Component para indicar que la clase UsuarioModelAssembler pueda ser inyectada en otro componentes
@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>>{//Es una clase que implementa RepresentationModelAssembler para comvertit un objeto usuario en un EntityModel
    //Usar la Anotacion @Override para indicar que el siguiente metodo implementa un metodo de la interfaz RepresentationModelAssembler
    @Override
    //Metodo EntityModel<Usuarios> para convertir un objeto de usuario y añadir los enlaces HATEOAS
    public @NonNull EntityModel<Usuario> toModel(Usuario usuario){
        return EntityModel.of(usuario,
        linkTo(methodOn(UsuarioControllerV2.class).registrar(null)).withSelfRel(),
        linkTo(methodOn(UsuarioControllerV2.class).login(usuario)).withRel("login"));
    }


}
