package com.example.PerfulandiaSPA.assemblers;
//Importar las clases necesarias para el Modelo y Controller
import com.example.PerfulandiaSPA.Model.Perfume;
import com.example.PerfulandiaSPA.Controller.StockControllerV2;


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
public class StockModelAssembler implements RepresentationModelAssembler<Perfume, EntityModel<Perfume>> {

    //Metodo EntityModel<Usuarios> para convertir un objeto de usuario y añadir los enlaces HATEOAS
    @Override
    public @NonNull EntityModel<Perfume> toModel(@NonNull Perfume perfume) {
        return EntityModel.of(perfume,
            linkTo(methodOn(StockControllerV2.class).consultarStock(0)).withRel("consultar"),
            linkTo(methodOn(StockControllerV2.class).reducirStock(0, 0)).withRel("reducir"),
            linkTo(methodOn(StockControllerV2.class).incrementarStock(0, 0)).withRel("incrementar"));
            
    }
    
}

