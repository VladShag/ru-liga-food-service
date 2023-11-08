package ru.liga.deliveryservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.liga.common.dto.RabbitSendOrderDTO;
import ru.liga.common.entity.Courier;
import ru.liga.common.entity.Status;
import ru.liga.common.exceptions.NoSuchEntityException;
import ru.liga.common.exceptions.WrongStatusException;
import ru.liga.common.repository.CourierRepository;
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
    private final CourierRepository courierRepository;
    private final RabbitMQProducerServiceImp rabbit;
    private final int DELIVERY_PAYMENT = 100;
    private final ObjectMapper mapper;
    private final String ROUTING_KEY_NOTIFICATION = "notification";
    private final List<String> availableStatus = Arrays.asList(Status.DELIVERY_PENDING.toString(), Status.DELIVERY_PICKING.toString(), Status.DELIVERY_DENIED.toString(),
            Status.DELIVERY_DELIVERING.toString(), Status.DELIVERY_REFUNDED.toString(), Status.DELIVERY_COMPLETE.toString());

    public DelieveryListDTO getDeliveriesByStatus(String status) {
        if(!availableStatus.contains(status)) {
            throw new WrongStatusException("You can't get orders by status: " + status + "!");
        }
        DelieveryListDTO delieveryListDTO = new DelieveryListDTO();
        List<DeliveryDTO> deliveries = new ArrayList<>();
        List<Order> orderList = orderRepository.findOrdersByStatus(status);
        if (orderList.isEmpty()) {
            throw new NoSuchEntityException("There is no available orders!");
        }
        for (Order orderInRepo : orderList) {
            deliveries.add(mapOrderToDelivery(orderInRepo));
        }
        delieveryListDTO.setDeliveries(deliveries);
        return delieveryListDTO;
    }
    @SneakyThrows
    public DeliveryDTO setDeliveryStatus(UUID id, String status) {
        if(!availableStatus.contains(status)) {
            throw new WrongStatusException("You can't set status: " + status + "!");
        }
        Order orderToChangeStatus = orderRepository.findOrderById(id).orElseThrow(() -> new NoSuchEntityException("No order with Id: " + id));
        String oldStatus = orderToChangeStatus.getStatus();
        orderToChangeStatus.setStatus(status);
        orderRepository.save(orderToChangeStatus);
        RabbitSendOrderDTO dtoToSend = new RabbitSendOrderDTO();
        dtoToSend.setOrderId(id);
        if(Status.DELIVERY_PICKING.toString().equals(status)) {
            if(!oldStatus.equals(Status.DELIVERY_PENDING.toString())) {
                throw new WrongStatusException("You can't set status on order, which is " + oldStatus);
            }
            dtoToSend.setQueueToSend("kitchen-service");
            dtoToSend.setMessage("Courier will pick this order!");
            rabbit.sendMessage(mapper.writeValueAsString(dtoToSend), ROUTING_KEY_NOTIFICATION);
        }
        if(Status.DELIVERY_COMPLETE.toString().equals(status)) {
            if(!oldStatus.equals(Status.DELIVERY_DELIVERING.toString())) {
                throw new WrongStatusException("You can't set status on order, which is " + oldStatus);
            }
            Courier courierToSetNewStatus = courierRepository.getCourierById(orderToChangeStatus.getCourierId()).orElseThrow(() -> new NoSuchEntityException("No courier with id: " + orderToChangeStatus.getCourierId()));
            courierToSetNewStatus.setStatus("COURIER_ACTIVE");
            courierRepository.save(courierToSetNewStatus);
            dtoToSend.setQueueToSend("order-service");
            dtoToSend.setMessage("Your order has delivered! Hope it was fast! :)");
            rabbit.sendMessage(mapper.writeValueAsString(dtoToSend), ROUTING_KEY_NOTIFICATION);
        }
        return mapOrderToDelivery(orderToChangeStatus);
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
