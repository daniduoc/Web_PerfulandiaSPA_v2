package com.example.PerfulandiaSPA.assemblers;

//Importar las clases necesarias para el Modelo y Controller

import com.example.PerfulandiaSPA.Model.Perfume;
import com.example.PerfulandiaSPA.Controller.CarritoControllerV2;

import jakarta.validation.constraints.NotNull;

//Importar la clase static para crear los enlaces HATEOAS
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

//Importar la clase EntityModel para usar los HATEOAS
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

//Importar la anotacion NonNull para  los metodos no acepten valores nulos
import org.springframework.lang.NonNull;

//Agregar la anotacion @Component para indicar que la clase PerfumeModelAssembler pueda ser inyectada en otro componentes
@Component
public class CarritoModelAssembler implements RepresentationModelAssembler<Perfume, EntityModel<Perfume>>{
    @Override
    public @NotNull EntityModel<Perfume> toModel(Perfume perfume){
        return EntityModel.of(perfume, 
        linkTo(methodOn(CarritoControllerV2.class).verCarrito()).withRel("carrito"),
        linkTo(methodOn(CarritoControllerV2.class).eliminarPerfume(perfume.getId())).withRel("eliminar"));
    }
    
}
