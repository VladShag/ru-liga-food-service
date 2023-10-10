package ru.liga.orderservice.exceptions;

public class NoSuchOrderException extends RuntimeException {
    public NoSuchOrderException(String msg) {
        super(msg);
    }
}
