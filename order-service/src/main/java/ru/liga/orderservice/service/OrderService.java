package ru.liga.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.orderservice.dto.FullOrderDTO;
import ru.liga.orderservice.dto.MainOrderListDTO;
import ru.liga.orderservice.dto.OrderCreatedDTO;
import ru.liga.orderservice.dto.OrderToCreateDTO;
import ru.liga.orderservice.entity.Order;
import ru.liga.orderservice.exceptions.NoSuchOrderException;
import ru.liga.orderservice.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;

    public MainOrderListDTO getAllOrders() {
        return new MainOrderListDTO();
    }
    public FullOrderDTO getOrderById(long id) {
        Order orderToGet = repository.orderRepo.get(id);
        if(orderToGet == null) throw new NoSuchOrderException("There is no order with id " + id);
        else return new FullOrderDTO();
    }
    public MainOrderListDTO getOrdersByStatus(String status) {
        List<Order> ordersToGet = new ArrayList<>();
        for(Order o : repository.orderRepo.values()) {
            if(o.getStatus().equals(status)) ordersToGet.add(o);
        }
        if(ordersToGet.size() == 0) {
            throw new NoSuchOrderException("There is no order with status " + status);
        }
        return new MainOrderListDTO();
    }
    public OrderCreatedDTO addNewOrder(OrderToCreateDTO dto) {
        return new OrderCreatedDTO();
    }
    public Order changeOrderInfo(long id, Order order) {
        Order orderToChange = repository.orderRepo.get(id);
        if(orderToChange == null) throw new NoSuchOrderException("There is no order with id " + order.getId());
        else {
            orderToChange.setCourierId(order.getCourierId());
            orderToChange.setCustomerId(order.getCustomerId());
            order.setTimestamp(order.getTimestamp());
            order.setStatus(order.getStatus());
            order.setRestaurantId(order.getRestaurantId());
            repository.orderRepo.put(orderToChange.getId(),orderToChange);
        }
        return orderToChange;
    }
}
