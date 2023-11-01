package ru.liga.orderservice.service.rabbitMQproducer;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQProducerServiceImp implements RabbitMQProducerService {

    private final RabbitTemplate rabbitTemplate;
    private final String EXCHANGE_TYPE = "directExchange";

    public void sendMessage(String message, String routingKey) {
        rabbitTemplate.convertAndSend(EXCHANGE_TYPE, routingKey, message);
        System.out.println("Message sent");
    }
}
