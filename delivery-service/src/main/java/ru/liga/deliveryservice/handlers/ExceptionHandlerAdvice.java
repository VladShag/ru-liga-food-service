package ru.liga.deliveryservice.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.liga.common.exceptions.NoSuchEntityException;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(NoSuchEntityException.class)
    public ResponseEntity<String> noSuchOrderExceptionHandler(NoSuchEntityException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
