package com.example.PerfulandiaSPA.Controller;

import com.example.PerfulandiaSPA.Model.Incidencia;
import com.example.PerfulandiaSPA.Service.IncidenciaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/incidencias")
@CrossOrigin
@Tag(name = "Incidencia", description = "Operaciones sobre el controller de incidencia")
public class IncidenciaController {
    @Autowired
    private IncidenciaService servicio;

    //Crea y guarda un reporte de incidencia 
    @Operation(summary = "Crea y guarda un reporte de incidencia ", description ="Crea y guarda los datos del reporte de incidencia en una lista")
    @PostMapping
    public Incidencia crear(@RequestBody Incidencia i) {
        return servicio.guardarIncidencia(i);
    }
    
    //Obtiene el listado total de reportes de incidencia
    @Operation(summary = "Total de reportes de incidencia", description ="Obtiene el listado total de reportes de incidencia")
    @GetMapping
    public List<Incidencia> obtenerTodas() {
        return servicio.listarIncidencias();
    }
}
