package ru.liga.notificationservice.dto;


import lombok.Data;

import java.util.UUID;

@Data
public class RabbitSendOrderDTO {
    private UUID orderId;
    private String queueToSend;
    private String message;
}
