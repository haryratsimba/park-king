package com.parkking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SpringDocConfiguration {
    
    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(new Info().title("Park-king API")
                .description("L'API REST de Park-king")
                .version("1.0"));
    }
    
}
