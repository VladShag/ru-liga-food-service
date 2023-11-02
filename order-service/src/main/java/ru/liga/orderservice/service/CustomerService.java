package ru.liga.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.common.entity.Customer;
import ru.liga.common.exceptions.NoSuchEntityException;
import ru.liga.common.repository.CustomerRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository repository;
    public List<Customer> getAllCustomers() {
        List<Customer> allCustomers = repository.findAll();
        if(allCustomers.isEmpty()) {
            throw new NoSuchEntityException("There is no customers!");
        }
        return allCustomers;
    }
    public Customer getCustomerById(long id) {
        return repository.getCustomerById(id).orElseThrow(() -> new NoSuchEntityException("There is no customer with id " + id));
    }
    public Customer saveNewCustomer(Customer customer) {
        repository.save(customer);
        return customer;
    }
    public Customer changeCustomerInfo(long id, Customer customer) {
        Customer customerToChange = repository.getCustomerById(id).orElseThrow(() -> new NoSuchEntityException("There is no customer with id " + id));
        customerToChange.setCoordinates(customer.getCoordinates());
        customerToChange.setEmail(customer.getEmail());
        customerToChange.setPhone(customer.getPhone());
        repository.save(customerToChange);
        return customerToChange;
    }
    public String deleteCustomer(long id) {
        Customer customerToDelete = repository.getCustomerById(id).orElseThrow(() -> new NoSuchEntityException("There is no customer with id " + id));
        repository.deleteById(id);
        return "Customer with id " + id + " was successfully deleted!";
    }
}
