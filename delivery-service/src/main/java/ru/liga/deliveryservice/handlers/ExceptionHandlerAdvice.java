package ru.liga.deliveryservice.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.liga.common.exceptions.NoSuchEntityException;
import ru.liga.common.exceptions.WrongStatusException;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(NoSuchEntityException.class)
    public ResponseEntity<String> noSuchOrderExceptionHandler(NoSuchEntityException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(WrongStatusException.class)
    public ResponseEntity<String> wrongStatusException(NoSuchEntityException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
    }
}
