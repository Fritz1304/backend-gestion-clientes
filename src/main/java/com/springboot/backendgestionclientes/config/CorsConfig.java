package com.springboot.backendgestionclientes.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Value("${FRONTEND_PROD_URL:}")
    private String frontendProdUrl;

    @Value("${FRONTEND_DEV_URL:http://localhost:5173}")
    private String frontendDevUrl;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                String[] allowedOrigins;

                // Si estamos en producción y tenemos la URL del frontend
                if (!frontendProdUrl.isEmpty()) {
                    allowedOrigins = new String[]{frontendProdUrl, frontendDevUrl};
                } else {
                    // Solo desarrollo
                    allowedOrigins = new String[]{frontendDevUrl};
                }

                registry.addMapping("/api/**")
                        .allowedOrigins(allowedOrigins)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}