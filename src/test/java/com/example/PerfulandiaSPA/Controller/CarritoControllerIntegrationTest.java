package com.example.PerfulandiaSPA.Controller;

import com.example.PerfulandiaSPA.Model.Perfume;
import com.example.PerfulandiaSPA.Service.PerfumeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(CarritoController.class)
public class CarritoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PerfumeService perfumeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void agregarPerfumeAlCarrito_returnSuccess() throws Exception {
        Perfume perfume = new Perfume();
        perfume.setId(1);
        perfume.setNombre("Chanel N°5");

        when(perfumeService.getPerfumeId(1)).thenReturn(Optional.of(perfume));

        mockMvc.perform(post("/api/v1/carrito/agregar/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Perfume agregado al carrito: Chanel N°5"));
    }

    @Test
    void verCarrito_returnList() throws Exception {
    Perfume perfume = new Perfume();
    perfume.setId(1);
    perfume.setNombre("Chanel N°5");
    
    // Mockear el estado inicial del carrito
    when(perfumeService.getPerfumeId(1)).thenReturn(Optional.of(perfume));
    
    mockMvc.perform(post("/api/v1/carrito/agregar/1"));
    
    mockMvc.perform(get("/api/v1/carrito"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[0].nombre").value("Chanel N°5"));
    }

    @Test
    void eliminarPerfumeDelCarrito_returnSuccess() throws Exception {
        mockMvc.perform(delete("/api/v1/carrito/eliminar/1"))
                .andExpect(status().isOk());
    }

    @Test
    void vaciarCarrito_returnSuccess() throws Exception {
        mockMvc.perform(delete("/api/v1/carrito/vaciar"))
                .andExpect(status().isOk())
                .andExpect(content().string("Carrito vaciado exitosamente"));
    }

    @Test
    void totalPerfumes_returnCount() throws Exception {
        mockMvc.perform(get("/api/v1/carrito/total"))
                .andExpect(status().isOk());
    }
}