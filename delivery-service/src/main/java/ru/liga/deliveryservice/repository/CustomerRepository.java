package ru.liga.deliveryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.liga.deliveryservice.entity.Customer;
import ru.liga.deliveryservice.entity.Order;

import java.util.List;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer getCustomerById(long id);

}
