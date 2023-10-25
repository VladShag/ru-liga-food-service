package ru.liga.deliveryservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.deliveryservice.dto.DelieveryListDTO;
import ru.liga.deliveryservice.dto.DeliveryDTO;
import ru.liga.deliveryservice.entity.Order;
import ru.liga.deliveryservice.entity.Status;
import ru.liga.deliveryservice.repository.CustomerRepository;
import ru.liga.deliveryservice.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final int DELIVERY_PAYMENT = 100; //Заглушка до рассчета цены

    public DelieveryListDTO getDeliveriesByStatus(String status) {
        DelieveryListDTO delieveryListDTO = new DelieveryListDTO();
        List<DeliveryDTO> deliveries = new ArrayList<>();
        List<Order> orderList = orderRepository.findOrdersByStatus(status);
        if (orderList.isEmpty()) {
            throw new RuntimeException("Test info");
        }
        for (Order orderInRepo : orderList) {
            deliveries.add(mapOrderToDelivery(orderInRepo));
        }
        delieveryListDTO.setDeliveries(deliveries);
        return delieveryListDTO;
    }

    public DeliveryDTO setDeliveryStatus(long id, Status status) {
        Order order = orderRepository.findOrderById(id).orElseThrow(() -> new RuntimeException("test info"));
        order.setStatus(status.toString());
        orderRepository.save(order);
        return mapOrderToDelivery(order);
    }

    public DeliveryDTO getDeliveryById(long id) {
        Order order = orderRepository.findOrderById(id).orElseThrow(() -> new RuntimeException("test info"));
        return mapOrderToDelivery(order);
    }

    private DeliveryDTO mapOrderToDelivery(Order order) {
        DeliveryDTO deliveryDTOToAdd = new DeliveryDTO();
        deliveryDTOToAdd.setCustomer(customerRepository.getCustomerById(order.getCustomerId()).orElseThrow(() -> new RuntimeException("test info")));
        deliveryDTOToAdd.setOrderId(order.getId());
        deliveryDTOToAdd.setRestaurant(order.getRestaurant());
        deliveryDTOToAdd.setPayment(DELIVERY_PAYMENT);
        return deliveryDTOToAdd;
    }

}
