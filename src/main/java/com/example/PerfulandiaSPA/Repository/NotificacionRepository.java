package com.example.PerfulandiaSPA.Repository;

import com.example.PerfulandiaSPA.Model.Notificacion;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class NotificacionRepository {
    private List<Notificacion> listaNotificaciones = new ArrayList<>();

    public NotificacionRepository() {
        // Notificaciones de ejemplo (consistentes con el modelo)
        listaNotificaciones.add(new Notificacion(1, "Bienvenido", "Gracias por registrarte en Perfulandia", LocalDateTime.now(), false));
        listaNotificaciones.add(new Notificacion(2, "Oferta Especial", "20% de descuento en tu primer pedido", LocalDateTime.now().minusHours(3), false));
    }

    public List<Notificacion> obtenerTodas() {
        return new ArrayList<>(listaNotificaciones); // Retorna copia para evitar modificaciones externas
    }

    public List<Notificacion> obtenerNoLeidas() {
        List<Notificacion> noLeidas = new ArrayList<>();
        for (Notificacion n : listaNotificaciones) {
            if (!n.isLeida()) {
                noLeidas.add(n);
            }
        }
        return noLeidas;
    }

    public Notificacion guardar(Notificacion notificacion) {
        // Generar ID secuencial (como en LibroRepository)
        int nuevoId = 1;
        for (Notificacion n : listaNotificaciones) {
            if (n.getIdNotificaciones() >= nuevoId) {
                nuevoId = n.getIdNotificaciones() + 1;
            }
        }
        
        notificacion.setIdNotificaciones(nuevoId);
        notificacion.setFecha(LocalDateTime.now());
        notificacion.setLeida(false);
        
        listaNotificaciones.add(notificacion);
        return notificacion;
    }

    public void marcarComoLeida(int id) {
        for (Notificacion n : listaNotificaciones) {
            if (n.getIdNotificaciones() == id) {
                n.setLeida(true);
                break;
            }
        }
    }

    public void marcarTodasComoLeidas() {
        for (Notificacion n : listaNotificaciones) {
            n.setLeida(true);
        }
    }

    public int totalNotificaciones() {
        return listaNotificaciones.size();
    }
}