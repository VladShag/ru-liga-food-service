package ru.liga.kitchenservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.liga.common.entity.Order;
import ru.liga.common.entity.Status;
import ru.liga.common.exceptions.NoSuchEntityException;
import ru.liga.common.repository.OrderRepository;
import ru.liga.kitchenservice.dto.OrderToKitchenDTO;
import ru.liga.kitchenservice.service.rabbitMQproducer.RabbitMQProducerServiceImp;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ObjectMapper mapper;
    private final OrderRepository orderRepository;
    private final RabbitMQProducerServiceImp rabbit;

    public OrderToKitchenDTO getOrderById(long id) {
        Order orderToPrepare = orderRepository.findOrderById(id).orElseThrow(() -> new NoSuchEntityException("There is no order with id " + id));
        OrderToKitchenDTO dto = new OrderToKitchenDTO();
        dto.setItemList(orderToPrepare.getItems());
        return dto;
    }
    @SneakyThrows
    public void setOrderStatus(long id, Status status) {
        Order orderToChange = orderRepository.findOrderById(id).orElseThrow(() -> new NoSuchEntityException("There is no order with id " + id));
        orderToChange.setStatus(status.toString());
        orderRepository.save(orderToChange);
        if (Status.DELIVERY_PENDING.toString().equals(status.toString())) {
            rabbit.sendMessage(mapper.writeValueAsString(orderToChange), "couriers");
        }
        if(Status.KITCHEN_DENIED.toString().equals(status.toString())) {
            rabbit.sendMessage("Sorry, your order is being canceled", "customers");
        }
    }
    @RabbitListener(queues = "kitchen-service")
    public void processMyQueue(String message) {
        System.out.println(message);
    }
}
