package ru.liga.deliveryservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.liga.common.dto.RabbitSendOrderDTO;
import ru.liga.common.entity.Status;
import ru.liga.common.exceptions.NoSuchEntityException;
import ru.liga.common.exceptions.WrongStatusException;
import ru.liga.deliveryservice.dto.DelieveryListDTO;
import ru.liga.deliveryservice.dto.DeliveryDTO;
import ru.liga.common.entity.Order;
import ru.liga.common.repository.CustomerRepository;
import ru.liga.common.repository.OrderRepository;
import ru.liga.deliveryservice.service.rabbitMQproducer.RabbitMQProducerServiceImp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final RabbitMQProducerServiceImp rabbit;
    private final int DELIVERY_PAYMENT = 100;
    private final ObjectMapper mapper;
    private final String ROUTING_KEY_NOTIFICATION = "notification";
    private final List<String> availableStatus = Arrays.asList(Status.DELIVERY_PICKING.toString(), Status.DELIVERY_DENIED.toString(),
            Status.DELIVERY_DELIVERING.toString(), Status.DELIVERY_REFUNDED.toString(), Status.DELIVERY_COMPLETE.toString());

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

    public DeliveryDTO mapOrderToDelivery(Order order) {
        DeliveryDTO deliveryDTOToAdd = new DeliveryDTO();
        deliveryDTOToAdd.setCustomer(customerRepository.getCustomerById(order.getCustomerId()).orElseThrow(() -> new NoSuchEntityException("There is no customer with ID: " + order.getCustomerId())));
        deliveryDTOToAdd.setOrderId(order.getId());
        deliveryDTOToAdd.setRestaurant(order.getRestaurant());
        deliveryDTOToAdd.setPayment(DELIVERY_PAYMENT);
        return deliveryDTOToAdd;
    }

    @SneakyThrows
    public DeliveryDTO setDeliveryStatus(UUID id, String status) {
        if(!availableStatus.contains(status)) {
            throw new WrongStatusException("You can't set status: " + status + "!");
        }
        Order order = orderRepository.findOrderById(id).orElseThrow(() -> new NoSuchEntityException("No order with Id: " + id));
        order.setStatus(status);
        orderRepository.save(order);
        RabbitSendOrderDTO dtoToSend = new RabbitSendOrderDTO();
        dtoToSend.setOrderId(id);
        if(Status.DELIVERY_PICKING.toString().equals(status)) {
            dtoToSend.setQueueToSend("kitchen-service");
            dtoToSend.setMessage("Courier will pick this order!");
            rabbit.sendMessage(mapper.writeValueAsString(dtoToSend), ROUTING_KEY_NOTIFICATION);
        }
        if(Status.DELIVERY_COMPLETE.toString().equals(status)) {
            dtoToSend.setQueueToSend("order-service");
            dtoToSend.setMessage("Your order has delievered! Hope it was fast! :)");
        }
        return mapOrderToDelivery(order);
    }

}
