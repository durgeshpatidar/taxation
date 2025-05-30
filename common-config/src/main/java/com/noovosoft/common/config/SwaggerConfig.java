package com.noovosoft.common.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI baseOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Noovosoft Microservices API")
                .description("Reusable Swagger Documentation")
                .version("1.0.0")
                .contact(new Contact().name("Noovosoft").email("support@noovosoft.com"))
            )
            .externalDocs(new ExternalDocumentation()
                .description("Noovosoft Docs")
                .url("https://docs.noovosoft.com"));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
            .group("noovosoft")
            .packagesToScan("com.noovosoft")
            .build();
    }
}
