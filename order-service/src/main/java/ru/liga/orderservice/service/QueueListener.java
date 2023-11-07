package ru.liga.orderservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.liga.common.dto.RabbitSendOrderDTO;
import ru.liga.orderservice.service.rabbitMQproducer.RabbitMQProducerServiceImp;

@Service
@RequiredArgsConstructor
public class QueueListener {
    private final ObjectMapper mapper;

    @RabbitListener(queues = "order-service")
    @SneakyThrows
    public void processMyQueue(String message) {
        System.out.println(mapper.readValue(message, RabbitSendOrderDTO.class));
    }
}
