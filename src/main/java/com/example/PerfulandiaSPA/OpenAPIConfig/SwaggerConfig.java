package com.example.PerfulandiaSPA.OpenAPIConfig;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI PerfulandiaSPAOPENAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API - Gestion de perfumeria")
                .description("Microservicios API REST para gestionar catalogo de perfumes, usuarios, notificaciones, reporte de incidencia y carrito")
                .version("v2.0"));
    }

}

