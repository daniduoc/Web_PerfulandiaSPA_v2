package com.example.PerfulandiaSPA.Controller;

import com.example.PerfulandiaSPA.Model.Incidencia;
import com.example.PerfulandiaSPA.Model.Perfume;
import com.example.PerfulandiaSPA.Service.IncidenciaService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.text.html.parser.Entity;

//Importar las librerias de Swagger para la documentación de las API
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


//Importar assembler para HATEOAS
import com.example.PerfulandiaSPA.assemblers.IncidenciaModelAssembler;

//iMPORTAR LAS CLASES NECESARIAS PARA HATEOAS
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
//importar las clases para mejorar los modelos de respuesta
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;

//import las clases de ResponseEntity para manejar las respuestas http
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/api/v1/incidencias")
@Tag(name="Reporte Incidecia", description="Operacion sobre reporte de incidencias ")
@CrossOrigin
public class IncidenciaControllerV2 {
    @Autowired
    private IncidenciaService servicio;

    @Autowired
    private IncidenciaModelAssembler assembler;

    //Crea y guarda un reporte de incidencia 
    @Operation(summary = "Crea y guarda un reporte de incidencia ", description ="Crea y guarda los datos del reporte de incidencia en una lista")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Incidencia>> crear(@RequestBody Incidencia i) {
        Incidencia creado = servicio.guardarIncidencia(i);
        return ResponseEntity
                .created(linkTo(methodOn(IncidenciaControllerV2.class).crear(creado)).toUri())
                .body(assembler.toModel(creado));
    }

    //Busca un reporte de incidencia 
    @Operation(summary = "Busca un reporte de incidencia", description ="Busca un reporte de incidencia por su id en la lista")
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public Optional<Incidencia> buscarPorId(@PathVariable int id) {
        return servicio.buscarIncidencia(id);
    }
    
    //Obtiene el listado total de reportes de incidencia
    @Operation(summary = "Total de reportes de incidencia", description ="Obtiene el listado total de reportes de incidencia")
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Incidencia>> obtenerTodas() {
        List<EntityModel<Incidencia>> incidencia = servicio.listarIncidencias().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        return CollectionModel.of(incidencia,
        linkTo(methodOn(IncidenciaControllerV2.class).obtenerTodas()).withSelfRel());
    }
}
