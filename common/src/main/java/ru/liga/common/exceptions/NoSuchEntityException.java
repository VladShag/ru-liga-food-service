package ru.liga.common.exceptions;

public class NoSuchEntityException extends RuntimeException {
    public NoSuchEntityException(String msg) {
        super(msg);
    }
}
