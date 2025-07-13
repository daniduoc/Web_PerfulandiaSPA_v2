package com.example.PerfulandiaSPA.Controller;

import com.example.PerfulandiaSPA.Model.Perfume;
import com.example.PerfulandiaSPA.Service.PerfumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//importar las librerias de swagger para la documetacion de las API

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/carrito")
//@Tag se usa para agrupar y etiquetar los controladores dentro de la documentacion
@Tag(name = "Carrito de Compras", description = "Operaciones sobre el carrito de compras")
public class CarritoController {
    private final List<Perfume> carrito = new ArrayList<>();
    
    @Autowired
    private PerfumeService perfumeService;

    @Operation(summary = "Agregar un producto al carrito de compras", description = "Añadir un perfume al carrito de compras por su ID")
    @PostMapping("/agregar/{id}")
    public String agregarPerfume(@PathVariable int id) {
        Optional<Perfume> perfumeOpt = perfumeService.getPerfumeId(id);
        if (perfumeOpt.isPresent()) {
            carrito.add(perfumeOpt.get());
            return "Perfume agregado al carrito: " + perfumeOpt.get().getNombre();
        }
        return "Perfume no encontrado, no se pudo agregar";
    }
    
    @Operation(summary = "Mostrar todos los productos del carrito de compras", description = "Muestra todos los perfumes agregados al carrito de compras")
    @GetMapping
    public List<Perfume> verCarrito() {
        return new ArrayList<>(carrito);
    }
    
    @Operation(summary = "Eliminar un producto del carrito de compras", description = "Eliminar un perfume al carrito de compras por su ID")
    @DeleteMapping("/eliminar/{id}")
    public String eliminarPerfume(@PathVariable int id) {
        boolean eliminado = carrito.removeIf(perfume -> perfume.getId() == id);
        return eliminado ? "Perfume eliminado del carrito" : "Perfume no encontrado en el carrito";
    }

    @Operation(summary = "Vaciar todos los productos del carrito de compras", description = "Elimina todos los perfumes del carrito de compra")
    @DeleteMapping("/vaciar")
    public String vaciarCarrito() {
        carrito.clear();
        return "Carrito vaciado exitosamente";
    }

    @Operation(summary = "Contar los productos en el carrito de compras", description = "Devuelve el total de perfumes dentro del carrito")
    @GetMapping("/total")
    public int totalPerfumes() {
        return carrito.size();
    }
}