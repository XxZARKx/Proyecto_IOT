package com.smartinventory.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    OpenAPI openAPI() {
        String scheme = "bearerAuth";
        return new OpenAPI()
                .info(new Info().title("Smart Inventory API").version("1.0.0").description("Control inteligente de inventario con IoT y analytics"))
                .schemaRequirement(scheme, new SecurityScheme().name(scheme).type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"))
                .addSecurityItem(new SecurityRequirement().addList(scheme));
    }
}
