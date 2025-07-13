package com.example.PerfulandiaSPA.assemblers;

//Importar las clases necesarias para el Modelo y Controller
import com.example.PerfulandiaSPA.Model.Perfume;
import com.example.PerfulandiaSPA.Controller.PerfumeControllerV2;

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
public class PerfumeModelAssembler implements RepresentationModelAssembler<Perfume, EntityModel<Perfume>>{
    @Override
    public @NonNull EntityModel<Perfume> toModel(Perfume perfume){
        //El metodo linkTo lo usamos para crear los enlaces HATEOAS para cada API y el methodOn reconoce el metodo REST del controller
        return EntityModel.of(perfume,
        linkTo(methodOn(PerfumeControllerV2.class).buscarPerfume(perfume.getId())).withSelfRel(),
        linkTo(methodOn(PerfumeControllerV2.class).listarPerfumes()).withRel("perfumes"),
        linkTo(methodOn(PerfumeControllerV2.class).eliminarPerfume(perfume.getId())).withRel("eliminar")
       // linkTo(methodOn(PerfumeControllerV2.class).actualizarPerfume(perfume.getId())).withRel("actualizar")
        );
    }
    
}
