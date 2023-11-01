package ru.liga.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import ru.liga.orderservice.dto.*;
import ru.liga.common.entity.Order;
import ru.liga.common.entity.OrderItem;
import ru.liga.common.entity.Status;
import ru.liga.common.exceptions.NoSuchOrderException;
import ru.liga.common.repository.OrderRepository;
import ru.liga.common.repository.RestaurantRepository;
import ru.liga.orderservice.service.rabbitMQproducer.RabbitMQProducerServiceImp;


import javax.validation.Valid;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;
    private final RestaurantRepository restaurantRepository;
    private final OrderItemService orderItemService;
    private final RabbitMQProducerServiceImp rabbit;
    private final long CUSTOMER_ID_MOCK = 13;
    private final String MOSCOW_NAME = "Moscow";

    public MainOrderListDTO getAllOrders() {
        List<Order> orders = repository.findAll();
        MainOrderListDTO DTOToReturn = new MainOrderListDTO();
        List<FullOrderDTO> listToReturn = new ArrayList<>();
        for (Order orderInRepo : orders) {
            FullOrderDTO orderDTO = new FullOrderDTO();
            orderDTO.setId(orderInRepo.getId());
            orderDTO.setRestaurant(orderInRepo.getRestaurant());
            orderDTO.setTimestamp(orderInRepo.getTimestamp());
            orderDTO.setItems(mapItemToItemToShowDTO(orderInRepo.getItems()));
            listToReturn.add(orderDTO);
        }
        DTOToReturn.setOrders(listToReturn);
        return DTOToReturn;
    }

    public FullOrderDTO getOrderById(long id) {
        Order order = checkIfOrderExist(id);
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
        if (ordersByStatus.isEmpty()) {
            throw new NoSuchOrderException("There is no orders with status " + status);
        }
        List<OrderByStatusDTO> orderByStatusDTOS = new ArrayList<>();
        for (Order orderInRepo : ordersByStatus) {
            OrderByStatusDTO newOrder = new OrderByStatusDTO();
            newOrder.setId(orderInRepo.getId());
            List<ItemToAddDTO> itemList = new ArrayList<>();
            for (OrderItem i : orderInRepo.getItems()) {
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
        if (restaurantRepository.findRestaurantById(dto.getRestaurantId()).isPresent()) {
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
        repository.save(orderToChange);
        FullOrderDTO dtoToGive = new FullOrderDTO();
        dtoToGive.setItems(mapItemToItemToShowDTO(orderToChange.getItems()));
        dtoToGive.setRestaurant(orderToChange.getRestaurant());
        dtoToGive.setTimestamp(orderToChange.getTimestamp());
        if (Status.CUSTOMER_PAID.toString().equals(status.toString())) {
            rabbit.sendMessage("New order with id: " + orderToChange.getId() + " is waiting for acception", "restaurants");
        }
        return dtoToGive;
    }

    private static List<ItemToShowCustomerDTO> mapItemToItemToShowDTO(List<OrderItem> itemList) {
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

    private Order checkIfOrderExist(long id) {
        return repository.findOrderById(id).orElseThrow(() -> new NoSuchOrderException("There is no order with id: " + id));
    }
    private void sendMessageByStatus(Order order) {

    }
}
