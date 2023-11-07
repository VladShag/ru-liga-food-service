package ru.liga.orderservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.liga.common.entity.Customer;
import ru.liga.orderservice.service.CustomerService;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
@Tag(name = "Клиенты", description = "В данном контроллере описаны методы для взаимодействия с профилями клиентов")
public class CustomerRestController {
    private final CustomerService service;

    @GetMapping("/")
    @Operation(
            summary = "Список всех клиентов",
            description = "Метод, позволяющий получить список всех клиентов в базе"
    )
    public List<Customer> getAllCustomers() {
        return service.getAllCustomers();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Клиент по ID",
            description = "Метод, позволяющий получить информацию по клиенту по его идентификатору"
    )
    public Customer getCustomerById(@PathVariable("id") @Min(0) long id) {
        return service.getCustomerById(id);
    }

    @PostMapping("/")
    @Operation(
            summary = "Сохранить клиента",
            description = "Метод, позволяющий сохранить клиента в базе. Вся информация по клиенту передается в теле запроса."
    )
    public Customer saveNewCustomer(@RequestBody Customer customer) {
        return service.saveNewCustomer(customer);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Изменить данные клиента",
            description = "Метод, позволяющий изменить информацию о клиенте в базе. Вся информация по клиенту передается в теле запроса."
    )
    public Customer changeCustomerInfo(@PathVariable("id") @Min(0) long id, @RequestBody Customer customer) {
        return service.changeCustomerInfo(id, customer);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удалить клиента",
            description = "Метод, позволяющий удалить клиента из базы по его идентификатору."
    )
    public String deleteCustomer(@PathVariable("id") @Min(0) long id) {
        return service.deleteCustomer(id);
    }
}
