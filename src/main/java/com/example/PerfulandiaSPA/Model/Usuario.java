package com.example.PerfulandiaSPA.Model;

import java.util.Optional;

import jakarta.persistence.*;
import lombok.Data;

@Entity  //Indicar que la clase usuario es un objeto
@Data
public class Usuario {
    @Id // Generar el campo clave primaria de la tabla
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Genera el valor del id automatico
    private long id;

    private String nombre;
    private String email;
    private String password;

    public static Optional<Usuario> map(Object o){
        throw new UnsupportedOperationException("Uniplement method 'map");
    }
}