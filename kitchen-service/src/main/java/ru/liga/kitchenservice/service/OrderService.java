package ru.liga.kitchenservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.liga.common.dto.RabbitSendOrderDTO;
import ru.liga.common.entity.Order;
import ru.liga.common.entity.Status;
import ru.liga.common.exceptions.NoSuchEntityException;
import ru.liga.common.exceptions.WrongStatusException;
import ru.liga.common.repository.OrderRepository;
import ru.liga.kitchenservice.dto.OrderToKitchenDTO;
import ru.liga.kitchenservice.service.rabbitMQproducer.RabbitMQProducerServiceImp;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ObjectMapper mapper;
    private final OrderRepository orderRepository;
    private final RabbitMQProducerServiceImp rabbit;
    private final String ROUTING_KEY_NOTIFICATION = "notification";
    private final List<String> availableStatus = Arrays.asList(Status.KITCHEN_ACCEPTED.toString(), Status.KITCHEN_DENIED.toString(),
            Status.KITCHEN_PREPARING.toString(), Status.KITCHEN_REFUNDED.toString(), Status.DELIVERY_PENDING.toString());


    @SneakyThrows
    public OrderToKitchenDTO setOrderStatus(UUID id, String status) {
        if(!availableStatus.contains(status)) {
            throw new WrongStatusException("You can't set status: " + status + "!");
        }
        Order orderToChange = orderRepository.findOrderById(id).orElseThrow(() -> new NoSuchEntityException("There is no order with id " + id));
        orderToChange.setStatus(status);
        orderRepository.save(orderToChange);
        RabbitSendOrderDTO dto = new RabbitSendOrderDTO();
        dto.setOrderId(orderToChange.getId());
        if (Status.DELIVERY_PENDING.toString().equals(status)) {
            dto.setQueueToSend("delivery-service");
            dto.setMessage("Order is being prepared, waiting for delivery!");
            rabbit.sendMessage(mapper.writeValueAsString(dto), ROUTING_KEY_NOTIFICATION);
        }
        if (Status.KITCHEN_DENIED.toString().equals(status)) {
            dto.setQueueToSend("order-service");
            dto.setMessage("Заказ был отклонен рестораном!");
            rabbit.sendMessage(mapper.writeValueAsString(dto), ROUTING_KEY_NOTIFICATION);
        }
        OrderToKitchenDTO dtoToShow = new OrderToKitchenDTO();
        dtoToShow.setItemList(orderToChange.getItems());
        return dtoToShow;
    }

    @RabbitListener(queues = "kitchen-service")
    @SneakyThrows
    public void processMyQueue(String message) {
        System.out.println(mapper.readValue(message, RabbitSendOrderDTO.class));
    }
}
