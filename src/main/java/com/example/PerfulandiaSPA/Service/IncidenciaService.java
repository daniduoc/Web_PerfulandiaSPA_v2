package com.example.PerfulandiaSPA.Service;

import com.example.PerfulandiaSPA.Model.Incidencia;
import com.example.PerfulandiaSPA.Repository.IncidenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncidenciaService {
    @Autowired
    private IncidenciaRepository repo;

    public Incidencia guardarIncidencia(Incidencia i) {
        return repo.save(i);
    }

    public List<Incidencia> listarIncidencias() {
        return repo.findAll();
    }
}
