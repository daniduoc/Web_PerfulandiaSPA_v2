package com.example.PerfulandiaSPA.Controller;

import com.example.PerfulandiaSPA.Model.Perfume;
import com.example.PerfulandiaSPA.Service.PerfumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/carrito")
public class CarritoController {
    private final List<Perfume> carrito = new ArrayList<>();
    
    @Autowired
    private PerfumeService perfumeService;

    @PostMapping("/agregar/{id}")
    public String agregarPerfume(@PathVariable int id) {
        Optional<Perfume> perfumeOpt = perfumeService.getPerfumeId(id);
        if (perfumeOpt.isPresent()) {
            carrito.add(perfumeOpt.get());
            return "Perfume agregado al carrito: " + perfumeOpt.get().getNombre();
        }
        return "Perfume no encontrado, no se pudo agregar";
    }
    
    @GetMapping
    public List<Perfume> verCarrito() {
        return new ArrayList<>(carrito);
    }
    
    @DeleteMapping("/eliminar/{id}")
    public String eliminarPerfume(@PathVariable int id) {
        boolean eliminado = carrito.removeIf(perfume -> perfume.getId() == id);
        return eliminado ? "Perfume eliminado del carrito" : "Perfume no encontrado en el carrito";
    }

    @DeleteMapping("/vaciar")
    public String vaciarCarrito() {
        carrito.clear();
        return "Carrito vaciado exitosamente";
    }

    @GetMapping("/total")
    public int totalPerfumes() {
        return carrito.size();
    }
}