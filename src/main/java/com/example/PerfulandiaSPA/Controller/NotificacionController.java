package com.example.PerfulandiaSPA.Controller;

import com.example.PerfulandiaSPA.Model.Notificacion;
import com.example.PerfulandiaSPA.Service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    // Obtener todas las notificaciones
    @GetMapping
    public List<Notificacion> obtenerTodas() {
        return notificacionService.obtenerTodas();
    }

    // Obtener notificaciones no leídas
    @GetMapping("/noleidas")
    public List<Notificacion> obtenerNoLeidas() {
        return notificacionService.obtenerNoLeidas();
    }

    // Crear una nueva notificación
    @PostMapping("/crear")
    public Notificacion crearNotificacion(@RequestParam String titulo, @RequestParam String mensaje) {
        return notificacionService.crearNotificacion(titulo, mensaje);
    }

    // Marcar una notificación como leída
    @PostMapping("/leer/{id}")
    public String marcarComoLeida(@PathVariable int id) {
        notificacionService.marcarComoLeida(id);
        return "Notificación marcada como leída: ID " + id;
    }

    // Marcar todas como leídas
    @PostMapping("/leer/todas")
    public String marcarTodasComoLeidas() {
        notificacionService.marcarTodasComoLeidas();
        return "Todas las notificaciones fueron marcadas como leídas";
    }

    // Contar todas las notificaciones
    @GetMapping("/total")
    public int contarTodas() {
        return notificacionService.contarTodas();
    }

    // Contar las no leídas
    @GetMapping("/total/noleidas")
    public int contarNoLeidas() {
        return notificacionService.contarNoLeidas();
    }
}
