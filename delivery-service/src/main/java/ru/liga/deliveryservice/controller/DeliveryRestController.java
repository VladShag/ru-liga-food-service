package ru.liga.deliveryservice.controller;

import com.sun.xml.bind.v2.TODO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.liga.deliveryservice.dto.ChangeStatusDTO;
import ru.liga.deliveryservice.dto.DelieveryListDTO;
import ru.liga.deliveryservice.dto.DeliveryDTO;
import ru.liga.deliveryservice.dto.FullOrderDTO;
import ru.liga.common.entity.Status;
import ru.liga.deliveryservice.feign.CoreFeign;
import ru.liga.deliveryservice.service.DeliveryService;

import javax.annotation.security.RolesAllowed;

@RestController
@RequiredArgsConstructor
@RequestMapping("/delivery")
@Tag(name = "Доставки", description = "В данном контроллере описаны методы для взаимодействия с заказами на доставке")
public class DeliveryRestController {
    private final DeliveryService service;
    private final CoreFeign feign;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_COURIER')")
    @Operation(
            summary = "Получить заказ по ID",
            description = "Метод, позволяющий получить заказ на доставку по ID"
    )
    public DeliveryDTO getDeliveryById(@PathVariable("id") long id) {
        return service.getDeliveryById(id);
    }

    @GetMapping("/")
    @Operation(
            summary = "Получить заказы по cтатусу",
            description = "Метод, позволяющий получить список заказов на доставку по статусу"
    )
    public DelieveryListDTO getDeliveriesByStatus(@RequestParam("status") String status) {
        return service.getDeliveriesByStatus(status);
    }

    @PostMapping("/{id}")
    @Operation(
            summary = "Установить статус доставки",
            description = "Метод, позволяющий установить статус заказа"
    )
    public DeliveryDTO setDeliveryStatus(@PathVariable("id") long id, @RequestBody ChangeStatusDTO dto) {
        FullOrderDTO fullOrderDTO = feign.setOrderStatus(id, dto);
        return service.getDeliveryById(fullOrderDTO.getId());
    }
}
