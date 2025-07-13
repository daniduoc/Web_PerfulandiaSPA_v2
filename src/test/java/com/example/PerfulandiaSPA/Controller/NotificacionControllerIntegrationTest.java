package com.example.PerfulandiaSPA.Controller;

import com.example.PerfulandiaSPA.Model.Notificacion;
import com.example.PerfulandiaSPA.Service.NotificacionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(NotificacionController.class)
public class NotificacionControllerIntegrationTest{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificacionService notificacionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void obtenerTodasNotificaciones_returnList() throws Exception {
        mockMvc.perform(get("/api/v1/notificaciones"))
                .andExpect(status().isOk());
    }

    @Test
    void obtenerNoLeidas_returnList() throws Exception {
        mockMvc.perform(get("/api/v1/notificaciones/noleidas"))
                .andExpect(status().isOk());
    }

    @Test
    void crearNotificacion_returnNotificacion() throws Exception {
        mockMvc.perform(post("/api/v1/notificaciones/crear")
                .param("titulo", "Nueva notificación")
                .param("mensaje", "Este es un mensaje de prueba"))
                .andExpect(status().isOk());
    }

    @Test
    void marcarComoLeida_returnSuccess() throws Exception {
        mockMvc.perform(post("/api/v1/notificaciones/leer/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Notificación marcada como leída: ID 1"));
    }

    @Test
    void marcarTodasComoLeidas_returnSuccess() throws Exception {
        mockMvc.perform(post("/api/v1/notificaciones/leer/todas"))
                .andExpect(status().isOk())
                .andExpect(content().string("Todas las notificaciones fueron marcadas como leídas"));
    }

    @Test
    void contarTodasNotificaciones_returnCount() throws Exception {
        mockMvc.perform(get("/api/v1/notificaciones/total"))
                .andExpect(status().isOk());
    }

    @Test
    void contarNoLeidas_returnCount() throws Exception {
        mockMvc.perform(get("/api/v1/notificaciones/total/noleidas"))
                .andExpect(status().isOk());
    }
}