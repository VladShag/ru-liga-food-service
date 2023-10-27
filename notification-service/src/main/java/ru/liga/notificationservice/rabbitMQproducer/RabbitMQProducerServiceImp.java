package ru.liga.notificationservice.rabbitMQproducer;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQProducerServiceImp implements RabbitMQProducerService {

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(String message, String routingKey) {
        rabbitTemplate.convertAndSend("directExchange", routingKey, message);
        System.out.println("Message sent");
    }
}
