package ru.liga.deliveryservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.liga.common.entity.Courier;
import ru.liga.common.entity.CourierStatus;
import ru.liga.common.entity.Order;
import ru.liga.common.exceptions.NoSuchEntityException;
import ru.liga.common.repository.CourierRepository;
import ru.liga.common.repository.OrderRepository;
import ru.liga.deliveryservice.dto.OrderToDeliveryServiceDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class QueueListener {
    private final ObjectMapper mapper;
    private final OrderRepository orderRepository;
    private final CourierRepository courierRepository;
    private final double EARTH_RADIUS = 6371;

    @RabbitListener(queues = "delivery-service")
    @SneakyThrows
    public void processMyQueue(String message) {
        System.out.println("New order is waiting for delivery!");
        OrderToDeliveryServiceDTO order = mapper.readValue(message, OrderToDeliveryServiceDTO.class);
        setCourierToOrder(order);
    }

    private void setCourierToOrder(OrderToDeliveryServiceDTO orderDTO) {
        String coordinates = orderDTO.getRestaurantCoordinates();
        List<Courier> couriers = courierRepository.findAll();
        double minDist = Double.MAX_VALUE;
        long courierIdToPut = 0;
        Order orderToSetCourier = orderRepository.findOrderById(orderDTO.getId()).orElseThrow(() -> new NoSuchEntityException("There is no order with id: " + orderDTO.getId()));
        for (Courier courier : couriers) {
            if (courier.getStatus().equals("COURIER_ACTIVE")) {
                double distance = calcDistance(coordinates, courier.getCoordinates());
                if(distance < minDist) {
                    minDist = distance;
                    courierIdToPut = courier.getId();
                }
            }
        }
        orderToSetCourier.setCourierId(courierIdToPut);
        orderRepository.save(orderToSetCourier);
        Courier courier = courierRepository.getCourierById(courierIdToPut).orElseThrow(() -> new NoSuchEntityException("No courier with such id!"));
        courier.setStatus(CourierStatus.COURIER_BUSY.toString());
        courierRepository.save(courier);
        System.out.println("Courier with id " + courier.getId() + " will delieve order with id " + orderToSetCourier.getId());
    }
    private double calcDistance(String courierCoords, String restaurantCoords) {
        String[] coordinatesRestaurantSplitted = restaurantCoords.split(" ");
        String[] coordinatesCourierSplitted = courierCoords.split(" ");
        if(coordinatesRestaurantSplitted.length < 2 || coordinatesCourierSplitted.length < 2) {
            throw new RuntimeException("There is mistake in coordinates");
        }
        double resLat = Double.parseDouble(coordinatesRestaurantSplitted[0]);
        double resLon = Double.parseDouble(coordinatesRestaurantSplitted[1]);
        double courLat = Double.parseDouble(coordinatesCourierSplitted[0]);
        double courLon = Double.parseDouble(coordinatesCourierSplitted[1]);
        double difLat = Math.toRadians(courLat - resLat);
        double difLon = Math.toRadians(courLon - resLon);
        double distProm = Math.sin(difLat/2) * Math.sin(difLat/2) + Math.cos(Math.toRadians(resLat)) *
        Math.cos(Math.toRadians(courLat)) * Math.sin(difLon/2) * Math.sin(difLon/2);
        double distWithoutEarthRadius = 2 * Math.atan2(Math.sqrt(distProm), Math.sqrt(1 - distProm));
        return EARTH_RADIUS * distWithoutEarthRadius;
    }
}
