package ru.yandex.practicum.catsgram.exception;

public class IncorrectParameterException extends RuntimeException {
    String parameter;

    public IncorrectParameterException(String message, String parameter) {
        super(message);
        this.parameter = parameter;
    }

    public String getDetailMessage() {
        return getMessage() + ": " + parameter;
    }
}
