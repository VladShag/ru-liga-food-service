package ru.liga.deliveryservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.liga.deliveryservice.dto.FullOrderDTO;

@FeignClient(name = "order-service", url = "http://localhost:9001")
public interface CoreFeign {
    @GetMapping("/order/{id}")
    FullOrderDTO getData(@PathVariable long id);
}