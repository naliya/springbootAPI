package com.learning.springapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI userApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("User Management API")
                        .description("API for managing users with pagination, filtering, and validation")
                        .version("1.0.0"));
    }
}

//http://localhost:8080/swagger-ui.html


