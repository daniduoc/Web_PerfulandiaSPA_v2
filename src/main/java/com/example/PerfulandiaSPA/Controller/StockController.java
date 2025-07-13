package com.example.PerfulandiaSPA.Controller;


import com.example.PerfulandiaSPA.Service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/stock")
@CrossOrigin
public class StockController {
    
    @Autowired
    private StockService stockService;

    @GetMapping("/{id}")
    public ResponseEntity<Integer> consultarStock(@PathVariable int id) {
        return ResponseEntity.ok(stockService.consultarStock(id));
    }

    @PutMapping("/{id}/incrementar/{cantidad}")
    public ResponseEntity<String> incrementarStock(
            @PathVariable int id,
            @PathVariable int cantidad) {
        return ResponseEntity.ok(stockService.ajustarStock(id, cantidad));
    }

    @PutMapping("/{id}/reducir/{cantidad}")
    public ResponseEntity<String> reducirStock(
            @PathVariable int id,
            @PathVariable int cantidad) {
        return ResponseEntity.ok(stockService.ajustarStock(id, -cantidad));
    }

    @GetMapping("/{id}/disponible/{cantidad}")
    public ResponseEntity<Boolean> verificarDisponibilidad(
            @PathVariable int id,
            @PathVariable int cantidad) {
        return ResponseEntity.ok(stockService.verificarDisponibilidad(id, cantidad));
    }
}