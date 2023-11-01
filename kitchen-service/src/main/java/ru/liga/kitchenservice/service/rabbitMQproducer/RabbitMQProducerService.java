package ru.liga.kitchenservice.service.rabbitMQproducer;

public interface RabbitMQProducerService {
    void sendMessage(String message, String routingKey);
}
