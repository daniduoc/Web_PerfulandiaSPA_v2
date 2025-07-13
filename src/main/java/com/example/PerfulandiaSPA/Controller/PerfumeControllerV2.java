package com.example.PerfulandiaSPA.Controller;

import com.example.PerfulandiaSPA.Model.Perfume;
import com.example.PerfulandiaSPA.Service.PerfumeService;
import com.example.PerfulandiaSPA.assemblers.PerfumeModelAssembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.media.MediaType;

@RestController
@RequestMapping("/api/v2/perfumes")
@CrossOrigin
public class PerfumeControllerV2 {
    @Autowired
    private PerfumeService perfumeService;

    //Inyectar el assembler de Libro
    @Autowired
    private PerfumeModelAssembler assembler;

    @Operation(summary = "Se listan los productos", description = "Despliega una lista de todos los perfumes")
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Perfume>> listarPerfumes() {
        //Obtener la lista de perfume y la convertiremos a EntityModel usando el assembler 
        List<EntityModel<Perfume>> perfume = perfumeService.getPerfumes().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        return CollectionModel.of(perfume,
            linkTo(methodOn(PerfumeControllerV2.class).listarPerfumes()).withSelfRel());
    }
    @Operation(summary = "Agrega un producto", description = "Agrega un nuevo perfume a la lista de perfumes disponibles")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Perfume>> agregarPerfume(@RequestBody Perfume perfume) {
        Perfume crear = perfumeService.savePerfume(perfume);
        return ResponseEntity
        .created(linkTo(methodOn(PerfumeControllerV2.class).buscarPerfume(crear.getId())).toUri())
        .body(assembler.toModel(crear));
    }
    @Operation(summary = "Buscar productos por ID", description = "Busca un perfume dentro de la lista por ID")
    @GetMapping(value = "{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Perfume>> buscarPerfume(@PathVariable int id) {
    Optional<Perfume> perfumeOpt = perfumeService.getPerfumeId(id);
    if (perfumeOpt.isPresent()) {
        return ResponseEntity.ok(assembler.toModel(perfumeOpt.get()));
    } else {
        return ResponseEntity.notFound().build(); // HTTP 404
    }
}
    @Operation(summary = "Actualidar datos de un producto del catalogo", description = "Actualiza el detalle de un perfume existente")
    @PutMapping(value = "/{id}", produces =  MediaTypes.HAL_JSON_VALUE )
    public ResponseEntity<EntityModel<Perfume>> actualizarPerfume(@PathVariable int id, @RequestBody Perfume perfume){
        perfume.setId(id);
        Perfume actualzado = perfumeService.updatePerfume(perfume);
        return ResponseEntity.ok(assembler.toModel(actualzado));

    }
        

    @Operation(summary = "Eliminar un producto del catalogo", description = "Eliminar un perfume dentro del catalogo por ID")
    @DeleteMapping(value = "{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> eliminarPerfume(@PathVariable int id) {
        perfumeService.deletePerfume(id);
        return ResponseEntity.noContent().build();
        
    }
}