package com.example.PerfulandiaSPA.assemblers;

//importar la clase necesaria para el modelo y controlador 
import com.example.PerfulandiaSPA.Model.Notificacion;

import jakarta.validation.constraints.NotNull;

import com.example.PerfulandiaSPA.Controller.NotificacionControllerV2;

//Importar las clases static para crear los enlaces HATEAOS
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

//Importar la clase EntityModel para usar HATEOAS 
import org.springframework.hateoas.EntityModel;


//Importar la iteface de RepresentationModelAssembler para crear el ensamblador de NotificacionModelAssembler 
import org.springframework.hateoas.server.RepresentationModelAssembler;


//importar los estereotipos necesarios para el assembler 
import org.springframework.stereotype.Component;

//Importar la anotacion NotNull para indicar que el metoo no acepta valores nulos 
import org.springframework.lang.NonNull;

//Agregamos la anotacion Component para indicar que nuestra clase NotificacionModelAssembler es un cokmponente de spring
@Component
//La clase NotificacionModelAssembler debeb implementar a RepresentatationModelAssembler para convertir un objeto ed Notificación en EntityModel
public class NotificacionModelAssembler implements RepresentationModelAssembler<Notificacion, EntityModel<Notificacion>>{

    @Override
    public @NotNull EntityModel<Notificacion> toModel(Notificacion notificacion) {
        
        return EntityModel.of(notificacion,
        linkTo(methodOn(NotificacionControllerV2.class).obtenerTodas()).withRel("todas"),
        linkTo(methodOn(NotificacionControllerV2.class).obtenerNoLeidas()).withRel("No-Leidas"),
        linkTo(methodOn(NotificacionControllerV2.class).marcarComoLeida(0)).withRel("Marcar-Leida"),
        linkTo(methodOn(NotificacionControllerV2.class).marcarTodasComoLeidas()).withRel("Marcar-Todas-Leidas")
        );
    }
}
