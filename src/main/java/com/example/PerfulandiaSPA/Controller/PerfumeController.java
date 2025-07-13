package com.example.PerfulandiaSPA.Controller;

import com.example.PerfulandiaSPA.Model.Perfume;
import com.example.PerfulandiaSPA.Service.PerfumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/perfumes")
@CrossOrigin
public class PerfumeController {
    @Autowired
    private PerfumeService perfumeService;

    @GetMapping
    public List<Perfume> listarPerfumes() {
        return perfumeService.getPerfumes();
    }

    @PostMapping
    public Perfume agregarPerfume(@RequestBody Perfume perfume) {
        return perfumeService.savePerfume(perfume);
    }

    @GetMapping("{id}")
    public ResponseEntity<Perfume> buscarPerfume(@PathVariable int id) {
        return perfumeService.getPerfumeId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
/*    @PutMapping("{id}")
    public ResponseEntity<Perfume> actualizarPerfume(
            @PathVariable int id,
            @RequestBody Perfume perfume) {
        
        if (!perfumeService.getPerfumeId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        perfume.setId(id); 
        return ResponseEntity.ok(perfumeService.updatePerfume(perfume));
    }
 */

    @DeleteMapping("{id}")
    public ResponseEntity<String> eliminarPerfume(@PathVariable int id) {
        if (!perfumeService.getPerfumeId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        perfumeService.deletePerfume(id);
        return ResponseEntity.ok("Perfume eliminado correctamente");
    }

    @GetMapping("/total")
    public ResponseEntity<Long> totalPerfumes() {
        return ResponseEntity.ok(perfumeService.totalPerfumes());
    }
}