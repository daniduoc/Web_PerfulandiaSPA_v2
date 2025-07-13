package com.example.PerfulandiaSPA.Controller;

import com.example.PerfulandiaSPA.Model.Notificacion;
import com.example.PerfulandiaSPA.Service.NotificacionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notificaciones")
@Tag(name = "Notificacion", description = "Operaciones sobre el controller de notificacion")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    // Obtener todas las notificaciones
    @Operation(summary = "Muestra el total e notifiones", description ="Muestra un listado de notificaciones marcando si se encuentran leido o no")
    @GetMapping
    public List<Notificacion> obtenerTodas() {
        return notificacionService.obtenerTodas();
    }

    // Obtener notificaciones no leídas
    @Operation(summary = "Muestra las notifiones que no se encuentran leidas", description ="Retorna las notificaciones que no se encuentran leidas")
    @GetMapping("/noleidas")
    public List<Notificacion> obtenerNoLeidas() {
        return notificacionService.obtenerNoLeidas();
    }

    // Crear una nueva notificación
     @Operation(summary = "Agregar una Notificación", description = "Agrega una nueva notificacion  a la lista de de notficacionnes")
    @PostMapping("/crear")
    public Notificacion crearNotificacion(@RequestParam String titulo, @RequestParam String mensaje) {
        return notificacionService.crearNotificacion(titulo, mensaje);
    }

    // Marcar una notificación como leída
    @Operation(summary = "Modificar una Notificación", description = "Modificar una notificacion  ya existente en la lista de de notficacionnes")
    @PostMapping("/leer/{id}")
    public String marcarComoLeida(@PathVariable int id) {
        notificacionService.marcarComoLeida(id);
        return "Notificación marcada como leída: ID " + id;
    }

    // Marcar todas como leídas
    @Operation(summary = "marcar Notificacines como leidas", description = "Marca el total de notificaciones como leidas")
    @PostMapping("/leer/todas")
    public String marcarTodasComoLeidas() {
        notificacionService.marcarTodasComoLeidas();
        return "Todas las notificaciones fueron marcadas como leídas";
    }

    // Contar todas las notificaciones
    @Operation(summary = "Muestra el total de  notificaciones", description = "Retorna la cantidad de notificaciones que se encuentran en la lista")
    @GetMapping("/total")
    public int contarTodas() {
        return notificacionService.contarTodas();
    }

    // Contar las no leídas
    @Operation(summary = "Total numerico de notificaciones no leidas", description = "Retorna la cantidad de notificaciones no leidas que se encuentran en la lista")
    @GetMapping("/total/noleidas")
    public int contarNoLeidas() {
        return notificacionService.contarNoLeidas();
    }
}
