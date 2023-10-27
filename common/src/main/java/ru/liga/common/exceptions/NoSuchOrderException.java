package ru.liga.common.exceptions;

public class NoSuchOrderException extends RuntimeException {
    public NoSuchOrderException(String msg) {
        super(msg);
    }
}
