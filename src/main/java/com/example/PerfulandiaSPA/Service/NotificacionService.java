package com.example.PerfulandiaSPA.Service;

import com.example.PerfulandiaSPA.Model.Notificacion;
import com.example.PerfulandiaSPA.Repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacionService {
    @Autowired
    private NotificacionRepository notificacionRepository;

    public List<Notificacion> obtenerTodas() {
        return notificacionRepository.obtenerTodas();
    }

    public List<Notificacion> obtenerNoLeidas() {
        return notificacionRepository.obtenerNoLeidas();
    }

    public Notificacion crearNotificacion(String titulo, String mensaje) {
        Notificacion nueva = new Notificacion();
        nueva.setTitulo(titulo);
        nueva.setMensaje(mensaje);
        return notificacionRepository.guardar(nueva);
    }

    public void marcarComoLeida(int id) {
        notificacionRepository.marcarComoLeida(id);
    }

    public void marcarTodasComoLeidas() {
        notificacionRepository.marcarTodasComoLeidas();
    }

    public int contarNoLeidas() {
        return notificacionRepository.obtenerNoLeidas().size();
    }

    public int contarTodas() {
        return notificacionRepository.totalNotificaciones();
    }
}