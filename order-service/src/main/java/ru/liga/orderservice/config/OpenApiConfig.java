package ru.liga.orderservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;

import java.util.Locale;

//@OpenAPIDefinition(
//        info = @Info(
//                title = "API сервиса заказов",
//                description = "В данном разделе храняться методы для Order Service и вся документация по ним",
//                version = "0.0.1",
//                contact = @Contact(
//                        name = "Vlad Shagalov",
//                        email = "vladnnovgorod@yandex.ru"
//                )
//
//        )
//)
public class OpenApiConfig {
    private SecurityScheme createApiKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP).bearerFormat("JWT").scheme("bearer");
    }
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes("Bearer Authentication", createApiKeyScheme()))
                .info(new Info().title("API сервиса заказов")
                        .description("В данном разделе храняться методы для Order Service и вся документация по ним")
                        .version("0.0.1").contact(new Contact().name("Vlad Shagalov").email("vladnnovgorod@yandex.ru"))
                        .license(new License().name("License of API").url("api")));

    }
}