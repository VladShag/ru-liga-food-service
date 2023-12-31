package ru.liga.deliveryservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.common.exceptions.NoSuchEntityException;
import ru.liga.deliveryservice.dto.DelieveryListDTO;
import ru.liga.deliveryservice.dto.DeliveryDTO;
import ru.liga.common.entity.Order;
import ru.liga.common.repository.CustomerRepository;
import ru.liga.common.repository.OrderRepository;

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

    public DeliveryDTO getDeliveryById(long id) {
        Order order = orderRepository.findOrderById(id).orElseThrow(() -> new NoSuchEntityException("There is no order with ID: " + id));
        return mapOrderToDelivery(order);
    }

    public DeliveryDTO mapOrderToDelivery(Order order) {
        DeliveryDTO deliveryDTOToAdd = new DeliveryDTO();
        deliveryDTOToAdd.setCustomer(customerRepository.getCustomerById(order.getCustomerId()).orElseThrow(() -> new NoSuchEntityException("There is no customer with ID: " + order.getCustomerId())));
        deliveryDTOToAdd.setOrderId(order.getId());
        deliveryDTOToAdd.setRestaurant(order.getRestaurant());
        deliveryDTOToAdd.setPayment(DELIVERY_PAYMENT);
        return deliveryDTOToAdd;
    }

}
