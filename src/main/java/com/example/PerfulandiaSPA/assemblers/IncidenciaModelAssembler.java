package com.example.PerfulandiaSPA.assemblers;

//Importar las clases necesarias para el moelo y controlador;
import com.example.PerfulandiaSPA.Model.Incidencia;
import com.example.PerfulandiaSPA.Controller.IncidenciaControllerV2;

//Importar la clase static EntityModel para crear los enlaces HATEOAS
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

//Importa la clase EntityModel para usar HATEOAOS
import org.springframework.hateoas.EntityModel;

//Importar la inteface RepresentationModelAssembler para crear el ensamblaor de PerfumeModelAssembler 
import org.springframework.hateoas.server.RepresentationModelAssembler;

//importar los estereotipo necesarios para el assembler 
import org.springframework.stereotype.Component;

//Imprtar la anotacion NonNulñl para inicar que el metodo no acwepta valores nulos
import org.springframework.lang.NonNull;

//Anotaciòn Component para indicar que nuestra clase PerfumeModelAssembler es un componente Spring
@Component

//La clase PerfuemeModelAssembler debe implementar a RepresentationModelAssembler para convertir un objeto en EntityModel
public class IncidenciaModelAssembler implements RepresentationModelAssembler<Incidencia, EntityModel<Incidencia>>{
    //Anotacion Override pra indicar que este metodo implementa un metodo de la interface RepresentationModelAssembler
    @Override
    //Método para convertir un objeto de Perfume en un EntityModel. Usamos la anotacion NotNull para no aceptar valores nulos, usmos linkTo con el metodo methodOn para crear los enklaces HATEAOS para bada metodo REST del controlador
    public @NonNull EntityModel<Incidencia> toModel (Incidencia incidencia) {
        return EntityModel.of(incidencia,
        linkTo(methodOn(IncidenciaControllerV2.class).buscarPorId(incidencia.getId())).withSelfRel(),
        linkTo(methodOn(IncidenciaControllerV2.class).crear(incidencia)).withSelfRel(),
        linkTo(methodOn(IncidenciaControllerV2.class).obtenerTodas()).withSelfRel()
        );
    }
}
