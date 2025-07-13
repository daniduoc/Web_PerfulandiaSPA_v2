package com.example.PerfulandiaSPA.Controller;


import com.example.PerfulandiaSPA.Service.StockService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/stock")
@CrossOrigin
@Tag(name = "Stock", description = "Operaciones sobre el controller de stock")
public class StockController {
    
    @Autowired
    private StockService stockService;

    //Consulta el stock de caa producto por id
    @Operation(summary = "Muestra el stock de un producto", description ="Muestra el total de stock de un producto en la lista")
    @GetMapping("/{id}")
    public ResponseEntity<Integer> consultarStock(@PathVariable int id) {
        return ResponseEntity.ok(stockService.consultarStock(id));
    }
    //Incrementa la cantidad de stock de un producto 
    @Operation(summary = "Incrementa el stock de un producto", description ="Incrementa el total de stock de un producto en la lista")
    @PutMapping("/{id}/incrementar/{cantidad}")
    public ResponseEntity<String> incrementarStock(
            @PathVariable int id,
            @PathVariable int cantidad) {
        return ResponseEntity.ok(stockService.ajustarStock(id, cantidad));
    }
    //Reduce  la cantidad de stock de un producto 
    @Operation(summary = "Reduce el stock de un producto", description ="Reduce el total de stock de un producto en la lista")
    @PutMapping("/{id}/reducir/{cantidad}")
    public ResponseEntity<String> reducirStock(
            @PathVariable int id,
            @PathVariable int cantidad) {
        return ResponseEntity.ok(stockService.ajustarStock(id, -cantidad));
    }
    

    //Segun el stock verifica si esta dispnible el producto
    @Operation(summary = "Verifica si esta dispnible el producto", description ="Segun el stock verifica si esta dispnible el producto")
    @GetMapping("/{id}/disponible/{cantidad}")
    public ResponseEntity<Boolean> verificarDisponibilidad(
            @PathVariable int id,
            @PathVariable int cantidad) {
        return ResponseEntity.ok(stockService.verificarDisponibilidad(id, cantidad));
    }
}