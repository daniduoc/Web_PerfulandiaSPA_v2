package com.example.PerfulandiaSPA.Model;

import java.time.LocalDateTime;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //Genera getters, setters, toString, equals, hashCode y un constructor con los campos requeridos.
@AllArgsConstructor // Genera un constructor con todos los campos.
@NoArgsConstructor // Genera un constructor con todos los campos.
public class Notificacion {
    private int idNotificaciones;
    private String titulo;
    private String mensaje;
    private LocalDateTime fecha;
    private boolean leida;
    
       
    
}
