package ru.liga.deliveryservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.deliveryservice.dto.DelieveryDTO;
import ru.liga.deliveryservice.entity.Delivery;
import ru.liga.deliveryservice.entity.Order;
import ru.liga.deliveryservice.repository.CustomerRepository;
import ru.liga.deliveryservice.repository.OrderRepository;
import ru.liga.deliveryservice.repository.RestaurantRepository;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;
    public DelieveryDTO getDeliveriesByStatus(String status) {
        DelieveryDTO deliveryDTO = new DelieveryDTO();
        List<Delivery> deliveries = new ArrayList<>();
        List<Order> orderList = orderRepository.findOrdersByStatus(status);
        for(Order o : orderList) {
            deliveries.add(mapOrderToDelivery(o));
        }
        deliveryDTO.setDeliveries(deliveries);
        return deliveryDTO;
    }
    public Delivery setDeliveryStatus(long id, String status) {
        Order order = orderRepository.findOrderById(id);
        order.setStatus(status);
        orderRepository.save(order);
        return mapOrderToDelivery(order);
    }

    public Delivery addNewDelivery(Delivery delivery) {
        return null;
    }
    public Delivery getDeliveryById(long id) {
        return null;
    }
    private Delivery mapOrderToDelivery(Order o) {
        Delivery deliveryToAdd = new Delivery();
        deliveryToAdd.setCustomer(customerRepository.getCustomerById(o.getCustomerId()));
        deliveryToAdd.setOrderId(o.getId());
        deliveryToAdd.setRestaurant(o.getRestaurant());
        deliveryToAdd.setPayment(100);
        return deliveryToAdd;
    }

}
