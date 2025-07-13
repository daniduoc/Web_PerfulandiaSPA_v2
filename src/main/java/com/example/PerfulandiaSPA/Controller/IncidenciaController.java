package com.example.PerfulandiaSPA.Controller;

import com.example.PerfulandiaSPA.Model.Incidencia;
import com.example.PerfulandiaSPA.Service.IncidenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/incidencias")
@CrossOrigin
public class IncidenciaController {
    @Autowired
    private IncidenciaService servicio;

    @PostMapping
    public Incidencia crear(@RequestBody Incidencia i) {
        return servicio.guardarIncidencia(i);
    }

    @GetMapping
    public List<Incidencia> obtenerTodas() {
        return servicio.listarIncidencias();
    }
}
