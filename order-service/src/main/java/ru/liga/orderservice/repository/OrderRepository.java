package ru.liga.orderservice.repository;

import ru.liga.orderservice.entity.Order;
import java.util.HashMap;

public class OrderRepository { ///Заглушка для проверки работоспособности Контроллера и Сервиса
    public HashMap<Long, Order> orderRepo;
    public long id;

    public OrderRepository() {
        orderRepo = new HashMap<>();
        id = 0L;
    }
    public Order addNewOrder(Order order) {
        orderRepo.put(order.getId(), order);
        return order;
    }
}
