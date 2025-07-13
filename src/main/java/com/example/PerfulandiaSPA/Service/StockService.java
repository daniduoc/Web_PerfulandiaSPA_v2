package com.example.PerfulandiaSPA.Service;

import com.example.PerfulandiaSPA.Model.Perfume;
import com.example.PerfulandiaSPA.Repository.PerfumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StockService {
      @Autowired
    private PerfumeRepository perfumeRepository;

    public int consultarStock(int id) {
        return perfumeRepository.findById(id)
                .map(Perfume::getStock)
                .orElse(0);
    }

    public String ajustarStock(int id, int cantidad) {
        Optional<Perfume> perfumeOpt = perfumeRepository.findById(id);
        if (perfumeOpt.isPresent()) {
            Perfume perfume = perfumeOpt.get();
            int nuevoStock = perfume.getStock() + cantidad;
            if (nuevoStock < 0) {
                return "No hay suficiente stock disponible";
            }
            perfume.setStock(nuevoStock);
            perfumeRepository.save(perfume);
            return "Stock actualizado correctamente";
        }
        return "Producto no encontrado";
    }

    public boolean verificarDisponibilidad(int id, int cantidad) {
        return perfumeRepository.findById(id)
                .map(p -> p.getStock() >= cantidad)
                .orElse(false);
    }
    
}
