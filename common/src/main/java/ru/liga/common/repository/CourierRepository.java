package ru.liga.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.common.entity.Courier;
import ru.liga.common.entity.Customer;

import java.util.Optional;

public interface CourierRepository extends JpaRepository<Courier, Long> {
    Optional<Courier> getCourierById(long id);

}