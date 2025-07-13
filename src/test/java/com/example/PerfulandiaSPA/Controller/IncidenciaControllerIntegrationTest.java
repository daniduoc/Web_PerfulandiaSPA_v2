package com.example.PerfulandiaSPA.Controller;

import com.example.PerfulandiaSPA.Model.Incidencia;
import com.example.PerfulandiaSPA.Service.IncidenciaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebMvcTest(IncidenciaController.class)
public class IncidenciaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IncidenciaService incidenciaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crearIncidencia_returnIncidencia() throws Exception {
        Incidencia incidencia = new Incidencia();
        incidencia.setDescripcion("Problema con el pedido");

        when(incidenciaService.guardarIncidencia(any(Incidencia.class))).thenReturn(incidencia);

        mockMvc.perform(post("/api/v1/incidencias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(incidencia)))
                .andExpect(status().isOk());
    }

    @Test
    void obtenerTodasIncidencias_returnList() throws Exception {
    Incidencia incidencia = new Incidencia();
    incidencia.setDescripcion("Problema con el pedido");
    
    when(incidenciaService.listarIncidencias()).thenReturn(List.of(incidencia));
    
    mockMvc.perform(get("/api/v1/incidencias"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[0].descripcion").value("Problema con el pedido"));
    }
}