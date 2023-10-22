package ru.liga.orderservice.service.rabbitMQproducer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducerServiceImp implements RabbitMQProducerService{

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMQProducerServiceImp(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String message, String routingKey) {
        rabbitTemplate.convertAndSend("directExchange", routingKey, message);
        System.out.println("Message sent");
    }
}
