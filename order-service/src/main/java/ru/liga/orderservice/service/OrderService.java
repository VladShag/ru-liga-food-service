package ru.liga.orderservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;


import ru.liga.common.dto.RabbitSendOrderDTO;
import ru.liga.common.entity.Order;
import ru.liga.common.entity.OrderItem;
import ru.liga.common.entity.Status;
import ru.liga.common.exceptions.NoSuchEntityException;
import ru.liga.common.repository.CustomerRepository;
import ru.liga.common.repository.OrderRepository;
import ru.liga.common.repository.RestaurantRepository;
import ru.liga.orderservice.dto.*;
import ru.liga.orderservice.service.rabbitMQproducer.RabbitMQProducerServiceImp;


import javax.validation.Valid;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;
    private final RestaurantRepository restaurantRepository;
    private final CustomerRepository customerRepository;
    private final OrderItemService orderItemService;
    private final RabbitMQProducerServiceImp rabbit;

    private final String ROUTING_KEY_NOTIFICATION = "notification";
    private final ObjectMapper mapper;

    public MainOrderListDTO getAllOrders() {
        List<Order> orders = repository.findAll();
        if(orders.isEmpty()) {
            throw new NoSuchEntityException("There is no orders!");
        }
        MainOrderListDTO DTOToReturn = new MainOrderListDTO();
        List<FullOrderDTO> listDTOOrdersToShow = new ArrayList<>();
        for (Order orderInRepo : orders) {
            FullOrderDTO orderDTO = new FullOrderDTO();
            orderDTO.setId(orderInRepo.getId());
            orderDTO.setRestaurant(orderInRepo.getRestaurant());
            orderDTO.setTimestamp(orderInRepo.getTimestamp());
            orderDTO.setItems(mapItemToItemToShowDTO(orderInRepo.getItems()));
            listDTOOrdersToShow.add(orderDTO);
        }
        DTOToReturn.setOrders(listDTOOrdersToShow);
        return DTOToReturn;
    }

    public FullOrderDTO getOrderById(UUID id) {
        Order order = checkIfOrderExist(id);
        FullOrderDTO orderDTO = new FullOrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setTimestamp(order.getTimestamp());
        orderDTO.setRestaurant(order.getRestaurant());
        orderDTO.setItems(mapItemToItemToShowDTO(order.getItems()));
        return orderDTO;
    }
    public OrderCreatedDTO addNewOrder(@Valid OrderToCreateDTO dto) {
        Order orderToAdd = new Order();
        orderToAdd.setTimestamp(new Date());
        OrderCreatedDTO orderCreatedDTOToShow = new OrderCreatedDTO();
        if (restaurantRepository.findRestaurantById(dto.getRestaurantId()).isPresent()) {
            orderToAdd.setRestaurant(restaurantRepository.findRestaurantById(dto.getRestaurantId()).get());
            orderToAdd.setStatus(Status.CUSTOMER_CREATED.toString());
            orderToAdd.setCustomerId(customerRepository.getCustomerById(dto.getRestaurantId()).orElseThrow(() -> new NoSuchEntityException("There is no customer with id" + dto.getCustomerId())).getId());
            UUID id = UUID.randomUUID();
            orderToAdd.setId(id);
            repository.save(orderToAdd);
            RabbitSendOrderDTO dtoToSend = new RabbitSendOrderDTO();
            dtoToSend.setOrderId(id);
            dtoToSend.setQueueToSend("notification");
            dtoToSend.setMessage("Новый заказ создан!");
            sendMessage(dtoToSend);
            orderToAdd.setItems(orderItemService.addNewOrderItems(dto, orderToAdd));
            orderCreatedDTOToShow.setId(id);
            orderCreatedDTOToShow.setSecretPaymentUrl("Url To Pay");
            orderCreatedDTOToShow.setEstimatedTimeOfArrival(new Time(10));
        }
        return orderCreatedDTOToShow;
    }
    @SneakyThrows
    public FullOrderDTO setOrderStatus(UUID id, String status) {
        Order orderToChange = checkIfOrderExist(id);
        orderToChange.setStatus(status);
        repository.save(orderToChange);
        FullOrderDTO dtoToGive = new FullOrderDTO();
        RabbitSendOrderDTO dtoToSend = new RabbitSendOrderDTO();
        dtoToGive.setId(orderToChange.getId());
        dtoToGive.setItems(mapItemToItemToShowDTO(orderToChange.getItems()));
        dtoToGive.setRestaurant(orderToChange.getRestaurant());
        dtoToGive.setTimestamp(orderToChange.getTimestamp());
        dtoToSend.setOrderId(orderToChange.getId());
        if (Status.CUSTOMER_PAID.toString().equals(status)) {
            dtoToSend.setQueueToSend("kitchen-service");
            dtoToSend.setMessage("Заказ был оплачен, ожидает подтверждения ресторана!");
            sendMessage(dtoToSend);
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
    @SneakyThrows
    private void sendMessage(RabbitSendOrderDTO dtoToSend) {
        rabbit.sendMessage(mapper.writeValueAsString(dtoToSend), ROUTING_KEY_NOTIFICATION);
    }

    public Order checkIfOrderExist(UUID id) {
        return repository.findOrderById(id).orElseThrow(() -> new NoSuchEntityException("There is no order with id: " + id));
    }
}
