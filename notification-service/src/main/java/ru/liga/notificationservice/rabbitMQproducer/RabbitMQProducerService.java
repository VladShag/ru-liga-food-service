package ru.liga.notificationservice.rabbitMQproducer;

public interface RabbitMQProducerService {
    void sendMessage(String message, String routingKey);
}
