package ru.liga.kitchenservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.liga.kitchenservice.controller.OrderController;

@Service
public class QueueListener {
//    @RabbitListener(queues = "kitchen")
//    public void processMyQueue(String message) {
//        System.out.println(message);
//    }
}
