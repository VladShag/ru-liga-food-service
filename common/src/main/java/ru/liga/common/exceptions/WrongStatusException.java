package ru.liga.common.exceptions;

public class WrongStatusException extends RuntimeException {
    public WrongStatusException(String msg) {
        super(msg);
    }
}