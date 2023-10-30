package ru.liga.deliveryservice.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class QueueListener {
    @RabbitListener(queues = "delivery-service")
    public void processMyQueue(String message) {
        System.out.println(message);
    }
}
