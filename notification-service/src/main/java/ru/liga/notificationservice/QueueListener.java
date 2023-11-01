package ru.liga.notificationservice;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.liga.notificationservice.rabbitMQproducer.RabbitMQProducerServiceImp;

@Service
@RequiredArgsConstructor
public class QueueListener {
    private final RabbitMQProducerServiceImp rabbit;
    @RabbitListener(queues = "restaurants")
    public void processMyQueue(String message) {
        System.out.println(message);
        rabbit.sendMessage(message, "kitchen-service");
    }


    @RabbitListener(queues = "customers")
    public void processMyQueue2(String message) {
        System.out.println(message);
        rabbit.sendMessage(message, "order-service");
    }
    @RabbitListener(queues = "couriers")
    public void processMyQueue3(String message) {
        System.out.println(message);
        rabbit.sendMessage(message, "delivery-service");
    }

}
