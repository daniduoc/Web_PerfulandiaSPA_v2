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

@WebMvcTest(PerfumeController.class)
public class PerfumeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PerfumeService perfumeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listarPerfumes_returnList() throws Exception {
        mockMvc.perform(get("/api/v1/perfumes"))
                .andExpect(status().isOk());
    }

    @Test
    void agregarPerfume_returnPerfume() throws Exception {
        Perfume perfume = new Perfume();
        perfume.setNombre("Dior Sauvage");

        when(perfumeService.savePerfume(any(Perfume.class))).thenReturn(perfume);

        mockMvc.perform(post("/api/v1/perfumes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(perfume)))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPerfumeExistente_returnPerfume() throws Exception {
        Perfume perfume = new Perfume();
        perfume.setId(1);

        when(perfumeService.getPerfumeId(1)).thenReturn(Optional.of(perfume));

        mockMvc.perform(get("/api/v1/perfumes/1"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPerfumeInexistente_returnNotFound() throws Exception {
        when(perfumeService.getPerfumeId(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/perfumes/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void eliminarPerfume_returnSuccess() throws Exception {
        when(perfumeService.getPerfumeId(1)).thenReturn(Optional.of(new Perfume()));

        mockMvc.perform(delete("/api/v1/perfumes/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Perfume eliminado correctamente"));
    }

    @Test
    void totalPerfumes_returnCount() throws Exception {
        mockMvc.perform(get("/api/v1/perfumes/total"))
                .andExpect(status().isOk());
    }
}