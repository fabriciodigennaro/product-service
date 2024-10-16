package com.challenge.productservice.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    OpenAPI openApi() {
        return new OpenAPI()
            .info(new Info()
                .title("Product service challenge")
                .version("1.0.0")
                .description("This is an API part of a challenge. Can be used to fetch product prices."));
    }
}
