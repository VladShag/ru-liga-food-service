package ru.liga.deliveryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.deliveryservice.entity.Customer;
import ru.liga.deliveryservice.entity.Order;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer getCustomerById(long id);

}
