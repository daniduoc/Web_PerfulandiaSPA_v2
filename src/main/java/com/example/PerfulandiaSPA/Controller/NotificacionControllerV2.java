package com.example.PerfulandiaSPA.Controller;

import com.example.PerfulandiaSPA.Model.Notificacion;
import com.example.PerfulandiaSPA.Service.NotificacionService;
import com.example.PerfulandiaSPA.assemblers.NotificacionModelAssembler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/notificaciones")
@Tag(name = "Notificacion", description = "Operaciones sobre notificaciones")
@CrossOrigin
public class NotificacionControllerV2 {

    @Autowired
    private NotificacionService notificacionService;

    @Autowired
    private NotificacionModelAssembler assembler;

    @Operation(summary = "Muestra todas las notificaciones", description = "Listado de todas las notificaciones con su estado de lectura")
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Notificacion>>> obtenerTodas() {
        List<EntityModel<Notificacion>> notificaciones = notificacionService.obtenerTodas()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(
                CollectionModel.of(notificaciones,
                        linkTo(methodOn(NotificacionControllerV2.class).obtenerTodas()).withSelfRel())
        );
    }

    @Operation(summary = "Muestra las notificaciones no leídas", description = "Retorna las notificaciones que no se encuentran leídas")
    @GetMapping(value = "/noleidas", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Notificacion>>> obtenerNoLeidas() {
        List<EntityModel<Notificacion>> notificaciones = notificacionService.obtenerNoLeidas()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(
                CollectionModel.of(notificaciones,
                        linkTo(methodOn(NotificacionControllerV2.class).obtenerNoLeidas()).withSelfRel())
        );
    }

    @Operation(summary = "Agregar una Notificación", description = "Agrega una nueva notificación a la lista")
    @PostMapping(value = "/crear", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Notificacion>> crearNotificacion(@RequestParam String titulo, @RequestParam String mensaje) {
        Notificacion nueva = notificacionService.crearNotificacion(titulo, mensaje);
        return ResponseEntity
                .created(linkTo(methodOn(NotificacionControllerV2.class).crearNotificacion(titulo, mensaje)).toUri())
                .body(assembler.toModel(nueva));
    }

    @Operation(summary = "Marcar una notificación como leída", description = "Marca una notificación existente como leída")
    @PostMapping(value = "/leer/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Notificacion>> marcarComoLeida(@PathVariable int id) {
        Notificacion leida = notificacionService.marcarComoLeida(id);
        return ResponseEntity.ok(assembler.toModel(leida));
    }

    @Operation(summary = "Marcar todas como leídas", description = "Marca todas las notificaciones como leídas")
    @PostMapping(value = "/leer/todas", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<String> marcarTodasComoLeidas() {
        notificacionService.marcarTodasComoLeidas();
        return ResponseEntity.ok("Todas las notificaciones fueron marcadas como leídas");
    }

    @Operation(summary = "Contar todas las notificaciones", description = "Retorna la cantidad de notificaciones en la lista")
    @GetMapping(value = "/total", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Integer> contarTodas() {
        return ResponseEntity.ok(notificacionService.contarTodas());
    }

    @Operation(summary = "Contar notificaciones no leídas", description = "Retorna la cantidad de notificaciones no leídas")
    @GetMapping(value = "/total/noleidas", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Integer> contarNoLeidas() {
        return ResponseEntity.ok(notificacionService.contarNoLeidas());
    }
}
