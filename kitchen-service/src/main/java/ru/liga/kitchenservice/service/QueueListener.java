package ru.liga.kitchenservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import ru.liga.common.dto.RabbitSendOrderDTO;
@RequiredArgsConstructor
public class QueueListener {
    private final ObjectMapper mapper;
    @RabbitListener(queues = "kitchen-service")
    @SneakyThrows
    public void processMyQueue(String message) {
        System.out.println("New Message!");
        RabbitSendOrderDTO dto = mapper.readValue(message, RabbitSendOrderDTO.class);
        System.out.println(dto.getMessage());
        System.out.println("ID Заказа: " + dto.getOrderId());
    }
}
