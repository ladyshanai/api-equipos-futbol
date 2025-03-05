package com.gestion.equiposfutbol.config;

import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiConfig {

    @Bean
    public OperationCustomizer removeAuthorizationHeader() {
        return (operation, handlerMethod) -> {
            if (operation.getParameters() != null) {
                operation.getParameters().removeIf(p -> "Authorization".equalsIgnoreCase(p.getName()));
            }
            return operation;
        };
    }
}

