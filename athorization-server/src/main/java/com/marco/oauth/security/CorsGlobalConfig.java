package com.marco.oauth.security;
import java.util.List; // <-- 4. IMPORTACIÓN AÑADIDA

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration; // <-- 3. ANOTACIÓN AÑADIDA
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource; // Interfaz correcta para el tipo de retorno
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

    @Configuration
    public class CorsGlobalConfig {

        @Bean // 2. La anotación @Bean va en el método
        CorsConfigurationSource corsConfigurationSource() { // 1. Toda la lógica va dentro de un método
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(List.of("http://localhost:4200"));
            config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH","DELETE", "OPTIONS"));//patch
            config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
            config.setAllowCredentials(true);
            //config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", config);
            return source;
        }
    }


