package ru.liga.orderservice.controller;

import org.springframework.web.bind.annotation.*;
import ru.liga.orderservice.entity.Order;
import ru.liga.orderservice.service.OrderService;

import java.util.List;

@RestController
public class OrderRestController {
    private final OrderService service = new OrderService();
    @GetMapping("/orders")
    public OrderResponse getAllOrders() {
        return new OrderResponse(service.getAllOrders());
    }
    @GetMapping("/order/{id}")
    public Order getOrderById(@PathVariable("id") long id) {
        return service.getOrderById(id);
    }
    @GetMapping("/order")
    public OrderResponse getOrdersByStatus(@RequestParam("status") String status){
        return new OrderResponse(service.getOrdersByStatus(status));
    }
    @PostMapping("/order")
    public Order addNewOrder(@RequestBody Order order) {
        return service.addNewOrder(order);
    }
    @PutMapping("/order/{id}")
    public Order changeOrderInfo(@PathVariable("id") long id, @RequestBody Order order) {
        return service.changeOrderInfo(id, order);
    }
    private class OrderResponse { ///Класс добавленный для корректного отображения в JSON данных в том формате,
                                ///который указан в материалах
        private List<Order> orders;
        private int page_index;
        private int page_count;
        OrderResponse(List<Order> orders) {
            this.orders = orders;
            this.page_count = 10; //По умолчанию значение- 10, после более плотной работы с бд данный показатель изменится
        }

        public List<Order> getOrders() {
            return orders;
        }

        public void setOrders(List<Order> orders) {
            this.orders = orders;
        }

        public int getPage_index() {
            return page_index;
        }

        public void setPage_index(int page_index) {
            this.page_index = page_index;
        }

        public int getPage_count() {
            return page_count;
        }

        public void setPage_count(int page_count) {
            this.page_count = page_count;
        }
    }
}
