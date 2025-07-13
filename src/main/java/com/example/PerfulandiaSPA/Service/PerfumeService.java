package com.example.PerfulandiaSPA.Service;

import com.example.PerfulandiaSPA.Model.Perfume;
import com.example.PerfulandiaSPA.Repository.PerfumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PerfumeService {
    @Autowired
    private PerfumeRepository perfumeRepository;

    public List<Perfume> getPerfumes() {
        return perfumeRepository.findAll();
    }

    public Perfume savePerfume(Perfume perfume) {
        return perfumeRepository.save(perfume);
    }

    public Optional<Perfume> getPerfumeId(int id) {
        return perfumeRepository.findById(id);
    }
    
    public Perfume updatePerfume(Perfume perfume) {
        return perfumeRepository.save(perfume);
    }

    public String deletePerfume(int id) {
        perfumeRepository.deleteById(id);
        return "Producto eliminado";
    }

    public long totalPerfumes() {
        return perfumeRepository.count();
    }
}