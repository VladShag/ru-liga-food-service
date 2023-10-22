package ru.liga.deliveryservice.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class QueueListener {
    @RabbitListener(queues = "Courier MSC")
    public void processMyQueue(String message) {
        System.out.println("message");
    }


    @RabbitListener(queues = "Courier_NN")
    public void processMyQueue2(String message) {
        System.out.println(message);
    }
}
