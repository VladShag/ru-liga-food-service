package ru.liga.notificationservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.liga.notificationservice.dto.RabbitSendOrderDTO;
import ru.liga.notificationservice.rabbitMQproducer.RabbitMQProducerServiceImp;

@Service
@RequiredArgsConstructor
public class QueueListener {
    private final RabbitMQProducerServiceImp rabbit;
    private final ObjectMapper mapper = new ObjectMapper();
    private final String NOTIFICATION_INFO = "notification";
    @RabbitListener(queues = "notification")
    public void processMyQueue(String message) throws JsonProcessingException {
        RabbitSendOrderDTO dto = mapper.readValue(message, RabbitSendOrderDTO.class);
        String path = dto.getQueueToSend();
        if(path.equals(NOTIFICATION_INFO)) {
            System.out.println("New message!");
            System.out.println(dto.getMessage());
            System.out.println("ID Заказа: " + dto.getOrderId());
        } else {
            rabbit.sendMessage(message, path);
        }
    }
}
