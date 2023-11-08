package ru.liga.kitchenservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.liga.common.dto.RabbitSendOrderDTO;
import ru.liga.common.entity.Order;
import ru.liga.common.entity.OrderItem;
import ru.liga.common.entity.Status;
import ru.liga.common.exceptions.NoSuchEntityException;
import ru.liga.common.exceptions.WrongStatusException;
import ru.liga.common.repository.OrderRepository;
import ru.liga.kitchenservice.dto.FullOrderDTO;
import ru.liga.kitchenservice.dto.ItemToShowCustomerDTO;
import ru.liga.kitchenservice.service.rabbitMQproducer.RabbitMQProducerServiceImp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ObjectMapper mapper;
    private final OrderRepository orderRepository;
    private final RabbitMQProducerServiceImp rabbit;
    private final String ROUTING_KEY_NOTIFICATION = "notification";
    private final List<String> availableStatus = Arrays.asList(Status.KITCHEN_ACCEPTED.toString(), Status.KITCHEN_DENIED.toString(),
            Status.KITCHEN_PREPARING.toString(), Status.KITCHEN_REFUNDED.toString(), Status.DELIVERY_PENDING.toString());


    public FullOrderDTO setOrderStatus(UUID id, String status) {
        if(!availableStatus.contains(status)) {
            throw new WrongStatusException("You can't set status: " + status + "!");
        }
        Order orderToChange = orderRepository.findOrderById(id).orElseThrow(() -> new NoSuchEntityException("There is no order with id " + id));
        String oldStatus = orderToChange.getStatus();
        orderToChange.setStatus(status);
        orderRepository.save(orderToChange);
        FullOrderDTO dtoToShow = new FullOrderDTO();
        dtoToShow.setTimestamp(orderToChange.getTimestamp());
        dtoToShow.setId(orderToChange.getId());
        dtoToShow.setItems(mapItemToItemToShowDTO(orderToChange.getItems()));
        RabbitSendOrderDTO dtoToSend = new RabbitSendOrderDTO();
        dtoToSend.setOrderId(orderToChange.getId());
        if (Status.DELIVERY_PENDING.toString().equals(status)) {
            if(oldStatus.equals(Status.KITCHEN_PREPARING.toString()) || oldStatus.equals(Status.KITCHEN_ACCEPTED.toString())) {
                dtoToSend.setQueueToSend("delivery-service");
                dtoToSend.setMessage("Order is being prepared, waiting for delivery!");
                sendMessage(dtoToSend);
            } else {
                throw new WrongStatusException("You can't set status: " + status + " on order which status is: " + oldStatus);
            }

        }
        if (Status.KITCHEN_DENIED.toString().equals(status)) {
            if(!oldStatus.equals(Status.CUSTOMER_PAID.toString())) {
                throw new WrongStatusException("You can't set status on order, which is " + oldStatus);
            }
            dtoToSend.setQueueToSend("order-service");
            dtoToSend.setMessage("Заказ был отклонен рестораном!");
            sendMessage(dtoToSend);
        }
        System.out.println(dtoToShow);
        return dtoToShow;
    }
    private List<ItemToShowCustomerDTO> mapItemToItemToShowDTO(List<OrderItem> itemList) {
        List<ItemToShowCustomerDTO> itemDTOList = new ArrayList<>();
        for (OrderItem item : itemList) {
            ItemToShowCustomerDTO itemDTO = new ItemToShowCustomerDTO();
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setImage(item.getRestaurantMenuItem().getImage());
            itemDTO.setDescription(item.getRestaurantMenuItem().getDescription());
            itemDTO.setPrice(item.getPrice());
            itemDTOList.add(itemDTO);
        }
        return itemDTOList;
    }
    @SneakyThrows
    private void sendMessage(RabbitSendOrderDTO dtoToSend) {
        rabbit.sendMessage(mapper.writeValueAsString(dtoToSend), ROUTING_KEY_NOTIFICATION);
    }
}
