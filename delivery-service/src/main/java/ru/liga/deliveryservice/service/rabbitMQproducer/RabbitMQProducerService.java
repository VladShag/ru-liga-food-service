package ru.liga.deliveryservice.service.rabbitMQproducer;

public interface RabbitMQProducerService {
    void sendMessage(String message, String routingKey);
}
