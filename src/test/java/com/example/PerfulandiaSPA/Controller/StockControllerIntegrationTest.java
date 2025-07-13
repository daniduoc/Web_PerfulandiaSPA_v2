package com.example.PerfulandiaSPA.Controller;

import com.example.PerfulandiaSPA.Service.StockService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(StockController.class)
public class StockControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockService stockService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void consultarStock_returnStock() throws Exception {
    when(stockService.consultarStock(1)).thenReturn(10);
    
    mockMvc.perform(get("/api/v1/stock/1"))
           .andExpect(status().isOk())
           .andExpect(content().string("10")); // Verificar el valor exacto
}

    @Test
    void incrementarStock_returnSuccess() throws Exception {
        when(stockService.ajustarStock(1, 5)).thenReturn("Stock incrementado exitosamente");

        mockMvc.perform(put("/api/v1/stock/1/incrementar/5"))
                .andExpect(status().isOk());
    }

    @Test
    void reducirStock_returnSuccess() throws Exception {
        when(stockService.ajustarStock(1, -3)).thenReturn("Stock reducido exitosamente");

        mockMvc.perform(put("/api/v1/stock/1/reducir/3"))
                .andExpect(status().isOk());
    }

    @Test
    void verificarDisponibilidad_returnBoolean() throws Exception {
        when(stockService.verificarDisponibilidad(1, 2)).thenReturn(true);

        mockMvc.perform(get("/api/v1/stock/1/disponible/2"))
                .andExpect(status().isOk());
    }
}