package ru.liga.kitchenservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.common.entity.Order;
import ru.liga.common.entity.Status;
import ru.liga.common.exceptions.NoSuchOrderException;
import ru.liga.common.repository.OrderRepository;
import ru.liga.kitchenservice.dto.OrderToKitchenDTO;
import ru.liga.kitchenservice.service.rabbitMQproducer.RabbitMQProducerServiceImp;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final String MOSCOW_NAME = "Moscow";
    private final OrderRepository orderRepository;
    private final RabbitMQProducerServiceImp rabbit;
    public OrderToKitchenDTO getOrderById(long id) {
        Order orderToPrepare = orderRepository.findOrderById(id).orElseThrow(() -> new NoSuchOrderException("There is no order with id " + id));
        OrderToKitchenDTO dto = new OrderToKitchenDTO();
        dto.setItemList(orderToPrepare.getItems());
        return dto;
    }
    public void setOrderStatus(long id, Status status) {
        Order orderToChange = orderRepository.findOrderById(id).orElseThrow(() -> new NoSuchOrderException("There is no order with id " + id));
        orderToChange.setStatus(status.toString());
        orderRepository.save(orderToChange);
        String address = orderToChange.getRestaurant().getAddress();
        if (Status.DELIVERY_PENDING.toString().equals(status.toString())) {
            if (address.contains(MOSCOW_NAME)) {
                rabbit.sendMessage("Order with id " + id + " waiting for delivery", "delivery.moscow");
            } else {
                rabbit.sendMessage("Order with id " + id + " waiting for delivery", "delivery.nizhniy_novgorod");
            }
        }
    }
}
