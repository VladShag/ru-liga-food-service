package ru.liga.orderservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

import java.util.Locale;

@OpenAPIDefinition(
        info = @Info(
                title = "API сервиса заказов",
                description = "В данном разделе храняться методы для Order Service и вся документация по ним",
                version = "0.0.1",
                contact = @Contact(
                        name = "Vlad Shagalov",
                        email = "vladnnovgorod@yandex.ru"
                )

        )
)
public class OpenApiConfig {
}
