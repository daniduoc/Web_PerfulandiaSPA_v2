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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//importar las librerias de swagger para la documetacion de las API

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/carrito")
//@Tag se usa para agrupar y etiquetar los controladores dentro de la documentacion
@Tag(name = "Carrito de Compras", description = "Operaciones sobre el carrito de compras")
public class CarritoControllerV2 {
    private final List<Perfume> carrito = new ArrayList<>();
    
    @Autowired
    private PerfumeService perfumeService;

    @Autowired
    private PerfumeModelAssembler assembler;

    @Operation(summary = "Agregar un producto al carrito de compras", description = "Añadir un perfume al carrito de compras por su ID")
    @PostMapping("/agregar/{id}")
    public ResponseEntity<String> agregarPerfume(@PathVariable int id) {
        Optional<Perfume> perfumeOpt = perfumeService.getPerfumeId(id);
        if (perfumeOpt.isPresent()) {
            carrito.add(perfumeOpt.get());
            return ResponseEntity.ok("Perfume agregado al carrito: " + perfumeOpt.get().getNombre());
        }
        return ResponseEntity.badRequest().body("Perfume no encontrado, no se pudo agregar");
    }
    
    @Operation(summary = "Mostrar todos los productos del carrito de compras", description = "Muestra todos los perfumes agregados al carrito de compras")
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Perfume>> verCarrito() {
        List<EntityModel<Perfume>> perfume = carrito.stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
            
        return CollectionModel.of(perfume,
                linkTo(methodOn(CarritoControllerV2.class).verCarrito()).withSelfRel());
    }
    
    @Operation(summary = "Eliminar un producto del carrito de compras", description = "Eliminar un perfume al carrito de compras por su ID")
    @DeleteMapping(value = "/eliminar/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<String> eliminarPerfume(@PathVariable int id) {
        boolean eliminado = carrito.removeIf(perfume -> perfume.getId() == id);
        return eliminado ? ResponseEntity.ok("Perfume eliminado del carrito")
        : ResponseEntity.badRequest().body("Perfume no estaba en el carrito");
    }

    @Operation(summary = "Vaciar todos los productos del carrito de compras", description = "Elimina todos los perfumes del carrito de compra")
    @DeleteMapping(value = "/vaciar", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<String> vaciarCarrito() {
        carrito.clear();
        return ResponseEntity.ok("Carrito vaciado");
    }

    @Operation(summary = "Contar los productos en el carrito de compras", description = "Devuelve el total de perfumes dentro del carrito")
    @GetMapping("/total")
    public int totalPerfumes() {
        return carrito.size();
    }
}