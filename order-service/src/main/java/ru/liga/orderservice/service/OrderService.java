package ru.liga.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.validation.annotation.Validated;
import ru.liga.orderservice.dto.*;
import ru.liga.orderservice.entity.Order;
import ru.liga.orderservice.entity.OrderItem;
import ru.liga.orderservice.entity.Status;
import ru.liga.orderservice.exceptions.NoSuchOrderException;
import ru.liga.orderservice.repository.OrderRepository;
import ru.liga.orderservice.repository.RestaurantRepository;
import ru.liga.orderservice.service.rabbitMQproducer.RabbitMQProducerServiceImp;


import javax.validation.Valid;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
@RequiredArgsConstructor
@Validated
public class OrderService {
    private final OrderRepository repository;
    private final RestaurantRepository restaurantRepository;
    private final OrderItemService orderItemService;
    private final RabbitMQProducerServiceImp rabbit;
    private final long CUSTOMER_ID_MOCK = 13;

    public MainOrderListDTO getAllOrders() {
        List<Order> orders = repository.findAll();
        MainOrderListDTO DTOToReturn = new MainOrderListDTO();
        List<FullOrderDTO> listToReturn = new ArrayList<>();
        for (Order o : orders) {
            FullOrderDTO orderDTO = new FullOrderDTO();
            orderDTO.setId(o.getId());
            orderDTO.setRestaurant(o.getRestaurant());
            orderDTO.setTimestamp(o.getTimestamp());
            orderDTO.setItems(mapItemToItemToShowDTO(o.getItems()));
            listToReturn.add(orderDTO);
        }
        DTOToReturn.setOrders(listToReturn);
        return DTOToReturn;
    }
    public FullOrderDTO getOrderById(long id) {
        Order order =checkIfOrderExist(id);
        FullOrderDTO orderDTO = new FullOrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setTimestamp(order.getTimestamp());
        orderDTO.setRestaurant(order.getRestaurant());
        orderDTO.setItems(mapItemToItemToShowDTO(order.getItems()));
        return orderDTO;
    }
    public MainOrderListDTO getOrdersByStatus(Status status) {
        String statusToString = status.toString();
        List<Order> ordersByStatus = repository.findOrdersByStatus(statusToString);
        if(ordersByStatus.size() == 0) {
            throw  new NoSuchOrderException("There is no orders with status " + status);
        }
        List<OrderByStatusDTO> orderByStatusDTOS = new ArrayList<>();
        for(Order o : ordersByStatus) {
            OrderByStatusDTO newOrder = new OrderByStatusDTO();
            newOrder.setId(o.getId());
            List<ItemToAddDTO> itemList = new ArrayList<>();
            for(OrderItem i : o.getItems()) {
                ItemToAddDTO itemDTO = new ItemToAddDTO();
                itemDTO.setMenuItemId(i.getRestaurantMenuItem().getId());
                itemDTO.setQuantity(i.getQuantity());
                itemList.add(itemDTO);
            }
            newOrder.setMenuItems(itemList);
            orderByStatusDTOS.add(newOrder);
        }
        MainOrderListDTO listToReturnDTO = new MainOrderListDTO();
        listToReturnDTO.setOrders(orderByStatusDTOS);
        return listToReturnDTO;
    }
    public OrderCreatedDTO addNewOrder(@Valid OrderToCreateDTO dto) {
        Order orderToAdd = new Order();
        orderToAdd.setTimestamp(new Date());
        OrderCreatedDTO orderCreatedDTO = new OrderCreatedDTO();
        if(restaurantRepository.findRestaurantById(dto.getRestaurantId()).isPresent()){
            orderToAdd.setRestaurant(restaurantRepository.findRestaurantById(dto.getRestaurantId()).get());
            orderToAdd.setStatus(Status.CUSTOMER_CREATED.toString());
            orderToAdd.setCustomerId(CUSTOMER_ID_MOCK); //заглушка пока не будет авторизации и автоматического присвоения id клиента
            repository.save(orderToAdd);
            orderToAdd.setItems(orderItemService.addNewOrderItems(dto, orderToAdd));
            orderCreatedDTO.setId(1);
            orderCreatedDTO.setSecretPaymentUrl("Some test string");
            orderCreatedDTO.setEstimatedTimeOfArrival(new Time(10));
        }
        return orderCreatedDTO;
    }
    public FullOrderDTO setOrderStatus(long id, Status status) {
        Order orderToChange = checkIfOrderExist(id);
        orderToChange.setStatus(status.toString());
        String address = orderToChange.getRestaurant().getAddress();
        repository.save(orderToChange);
        FullOrderDTO dtoToGive = new FullOrderDTO();
        dtoToGive.setItems(mapItemToItemToShowDTO(orderToChange.getItems()));
        dtoToGive.setRestaurant(orderToChange.getRestaurant());
        dtoToGive.setTimestamp(orderToChange.getTimestamp());
        if(status.toString().equals("KITCHEN_PREPARING")) {
            if(address.contains("Moscow")) {
                rabbit.sendMessage("Order with id " + id + " waiting for delivery", "delivery.moscow");
            } else {
                rabbit.sendMessage("Order with id " + id + " waiting for delivery", "delivery.nizhniy_novgorod");
            }
        }
        return dtoToGive;
    }
    private static List<ItemToShowCustomerDTO> mapItemToItemToShowDTO(List<OrderItem> itemList) {
        List<ItemToShowCustomerDTO> itemDTOList = new ArrayList<>();
        for(OrderItem item : itemList) {
            ItemToShowCustomerDTO itemDTO = new ItemToShowCustomerDTO();
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setImage(item.getRestaurantMenuItem().getImage());
            itemDTO.setDescription(item.getRestaurantMenuItem().getDescription());
            itemDTO.setPrice(item.getPrice());
            itemDTOList.add(itemDTO);
        }
        return itemDTOList;
    }
    private Order checkIfOrderExist(long id) {
        if(repository.existsById(id)) {
            return repository.findOrderById(id).get();
        } else {
            throw new NoSuchOrderException("There is no order with id: " + id);
        }
    }
}
