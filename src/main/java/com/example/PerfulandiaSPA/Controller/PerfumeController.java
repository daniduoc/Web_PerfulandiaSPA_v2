package com.example.PerfulandiaSPA.Controller;

import com.example.PerfulandiaSPA.Model.Perfume;
import com.example.PerfulandiaSPA.Service.PerfumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/perfumes")
@CrossOrigin
@Tag(name = "Perfume", description = "Operaciones sobre el controller de perfume")
public class PerfumeController {
    @Autowired
    private PerfumeService perfumeService;

    @Operation(summary = "Se listan los productos", description = "Despliega una lista de todos los perfumes")
    @GetMapping
    public List<Perfume> listarPerfumes() {
        return perfumeService.getPerfumes();
    }
    @Operation(summary = "Agrega un producto", description = "Agrega un nuevo perfume a la lista de perfumes disponibles")
    @PostMapping
    public Perfume agregarPerfume(@RequestBody Perfume perfume) {
        return perfumeService.savePerfume(perfume);
    }
    @Operation(summary = "Buscar productos por ID", description = "Busca un perfume dentro de la lista por ID")
    @GetMapping("{id}")
    public ResponseEntity<Perfume> buscarPerfume(@PathVariable int id) {
        return perfumeService.getPerfumeId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
  @PutMapping("{id}")
    public ResponseEntity<Perfume> actualizarPerfume(
            @PathVariable int id,
            @RequestBody Perfume perfume) {
        
        if (!perfumeService.getPerfumeId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        perfume.setId(id); 
        return ResponseEntity.ok(perfumeService.updatePerfume(perfume));
    }

    @Operation(summary = "Eliminar un producto del catalogo", description = "Eliminar un perfume dentro del catalogo por ID")
    @DeleteMapping("{id}")
    public ResponseEntity<String> eliminarPerfume(@PathVariable int id) {
        if (!perfumeService.getPerfumeId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        perfumeService.deletePerfume(id);
        return ResponseEntity.ok("Perfume eliminado correctamente");
    }
    @Operation(summary = "Contar el catalogo de productos", description = "Devuelve el numero total de perfumes dentro de la lista")
    @GetMapping("/total")
    public ResponseEntity<Long> totalPerfumes() {
        return ResponseEntity.ok(perfumeService.totalPerfumes());
    }
}