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
        DelieveryListDTO deliveryDTO = new DelieveryListDTO();
        List<DeliveryDTO> deliveries = new ArrayList<>();
        List<Order> orderList = orderRepository.findOrdersByStatus(status);
        for(Order orderInRepo : orderList) {
            deliveries.add(mapOrderToDelivery(orderInRepo));
        }
        deliveryDTO.setDeliveries(deliveries);
        return deliveryDTO;
    }
    public DeliveryDTO setDeliveryStatus(long id, Status status) {
        if(orderRepository.findOrderById(id).isPresent()) {
            Order order = orderRepository.findOrderById(id).get();
            order.setStatus(status.toString());
            orderRepository.save(order);
            return mapOrderToDelivery(order);
        } else {
            throw new RuntimeException();
        }
    }
    public DeliveryDTO getDeliveryById(long id) {
        if(orderRepository.findOrderById(id).isPresent()) {
            Order order = orderRepository.findOrderById(id).get();
            return mapOrderToDelivery(order);
        } else {
            throw new RuntimeException();
        }
    }
    private DeliveryDTO mapOrderToDelivery(Order order) {
        DeliveryDTO deliveryDTOToAdd = new DeliveryDTO();
        deliveryDTOToAdd.setCustomer(customerRepository.getCustomerById(order.getCustomerId()));
        deliveryDTOToAdd.setOrderId(order.getId());
        deliveryDTOToAdd.setRestaurant(order.getRestaurant());
        deliveryDTOToAdd.setPayment(DELIVERY_PAYMENT);
        return deliveryDTOToAdd;
    }

}
