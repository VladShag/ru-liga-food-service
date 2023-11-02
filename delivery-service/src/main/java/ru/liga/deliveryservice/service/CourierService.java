package ru.liga.deliveryservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.common.entity.Courier;
import ru.liga.common.entity.Order;
import ru.liga.common.entity.Status;
import ru.liga.common.exceptions.NoSuchOrderException;
import ru.liga.common.repository.CourierRepository;
import ru.liga.deliveryservice.dto.DeliveryDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourierService {
    private final CourierRepository repository;

    public List<Courier> getAllCouriers(){
        List<Courier> allCouriers = repository.findAll();
        if(allCouriers.isEmpty()){
            throw new NoSuchOrderException("Courier list is empty!");
        }
        return allCouriers;
    }
    public Courier getCourierById(long id) {
        return repository.getCourierById(id).orElseThrow(() -> new NoSuchOrderException("There is no courier with id: " + id));
    }
    public Courier saveCourier(Courier courier){
        long id = courier.getId();
        repository.save(courier);
        return repository.getCourierById(id).orElseThrow(() -> new NoSuchOrderException("There is no courier with id: " + id));
    }
    public Courier setCourierStatus(long id, String status) {
        Courier courier = repository.getCourierById(id).orElseThrow(() -> new NoSuchOrderException("There is no courier with id: " + id));
        courier.setStatus(status);
        repository.save(courier);
        return repository.getCourierById(id).orElseThrow(() -> new NoSuchOrderException("There is no courier with id: " + id));
    }
    public String deleteCourier(long id) {
        Courier courier = repository.getCourierById(id).orElseThrow(() -> new NoSuchOrderException("There is no courier with id: " + id));
        repository.deleteById(id);
        return "Courier successfully deleted!";
    }
}
