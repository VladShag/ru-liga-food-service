package ru.liga.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.orderservice.dto.*;
import ru.liga.orderservice.entity.Order;
import ru.liga.orderservice.entity.OrderItem;
import ru.liga.orderservice.repository.OrderRepository;
import ru.liga.orderservice.repository.RestaurantRepository;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;
    private final RestaurantRepository restaurantRepository;
    private final OrderItemService orderItemService;

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
        DTOToReturn.setPageCount(10);
        DTOToReturn.setPageIndex(0);
        return DTOToReturn;
    }
    public FullOrderDTO getOrderById(long id) {
        Order order = repository.findOrderById(id);
        FullOrderDTO orderDTO = new FullOrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setTimestamp(order.getTimestamp());
        orderDTO.setRestaurant(order.getRestaurant());
        orderDTO.setItems(mapItemToItemToShowDTO(order.getItems()));
        return orderDTO;
    }
    public MainOrderListDTO getOrdersByStatus(String status) {
        List<Order> ordersByStatus = repository.findOrdersByStatus(status);
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
        listToReturnDTO.setPageIndex(0);
        listToReturnDTO.setPageCount(10);
        return listToReturnDTO;
    }
    public OrderCreatedDTO addNewOrder(OrderToCreateDTO dto) {
        Order orderToAdd = new Order();
        orderToAdd.setTimestamp(new Date());
        orderToAdd.setRestaurant(restaurantRepository.findRestaurantById(dto.getRestaurantId()));
        orderToAdd.setStatus("active");
        orderToAdd.setCustomerId(13);
        orderToAdd.setCourierId(null);
        repository.save(orderToAdd);
        orderToAdd.setItems(orderItemService.addNewOrderItems(dto, orderToAdd));
        OrderCreatedDTO orderCreatedDTO = new OrderCreatedDTO();
        orderCreatedDTO.setId(1);
        orderCreatedDTO.setSecretPaymentUrl("Some test string");
        orderCreatedDTO.setEstimatedTimeOfArrival(new Time(10));
        return orderCreatedDTO;
    }
    private static List<ItemToShowCustomerDTO> mapItemToItemToShowDTO(List<OrderItem> itemList) {
        List<ItemToShowCustomerDTO> itemDTOList = new ArrayList<>();
        for(OrderItem i : itemList) {
            ItemToShowCustomerDTO itemDTO = new ItemToShowCustomerDTO();
            itemDTO.setQuantity(i.getQuantity());
            itemDTO.setImage(i.getRestaurantMenuItem().getImage());
            itemDTO.setDescription(i.getRestaurantMenuItem().getDescription());
            itemDTO.setPrice(i.getPrice());
            itemDTOList.add(itemDTO);
        }
        return itemDTOList;
    }
}
