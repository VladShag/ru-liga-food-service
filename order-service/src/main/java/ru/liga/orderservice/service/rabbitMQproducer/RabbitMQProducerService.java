package ru.liga.orderservice.service.rabbitMQproducer;

public interface RabbitMQProducerService {
    void sendMessage(String message, String routingKey);
}
