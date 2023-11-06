package ru.liga.deliveryservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.liga.common.entity.Courier;
import ru.liga.common.entity.CourierStatus;
import ru.liga.common.entity.Order;
import ru.liga.common.repository.CourierRepository;
import ru.liga.common.repository.OrderRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class QueueListener {
    private final ObjectMapper mapper;
    private final OrderRepository orderRepository;
    private final CourierRepository courierRepository;
    @RabbitListener(queues = "delivery-service")
    @SneakyThrows
    public void processMyQueue(String message) {
        System.out.println("New order is waiting for delivery!");
        Order order = mapper.readValue(message, Order.class);
        setCourierToOrder(order);
    }
    private void setCourierToOrder(Order order){
        String coordinates = order.getRestaurant().getCoordinates();
        List<Courier> couriers = courierRepository.findAll();
        String[] coordinatesSplitted = coordinates.split(" ");
        Double width = Double.valueOf(coordinatesSplitted[0]);
        Double length = Double.valueOf(coordinatesSplitted[0]);
        for (Courier courier : couriers){
            Double courierWidth = Double.valueOf(courier.getCoordinates().split(" ")[0]);
            Double courierLength = Double.valueOf(courier.getCoordinates().split(" ")[0]);
            if(Objects.equals(courierLength, length) && Objects.equals(courierWidth, width) && courier.getStatus().equals("COURIER_ACTIVE")){
                order.setCourierId(courier.getId());
                orderRepository.save(order);
                courier.setStatus(CourierStatus.COURIER_BUSY.toString());
                courierRepository.save(courier);
                System.out.println("Courier with id " + courier.getId() + " will delieve order with id " + order.getId());
            }
        }
    }
}
