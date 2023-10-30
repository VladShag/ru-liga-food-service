package ru.liga.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.liga.orderservice.service.rabbitMQproducer.RabbitMQProducerServiceImp;

@Service
@RequiredArgsConstructor
public class QueueListener {
    private final RabbitMQProducerServiceImp rabbit;
    @RabbitListener(queues = "customers")
    public void processMyQueue(String message) {
        System.out.println(message);
    }
}
