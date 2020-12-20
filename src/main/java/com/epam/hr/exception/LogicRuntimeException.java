package com.epam.hr.exception;

public class LogicRuntimeException extends RuntimeException{
    public LogicRuntimeException() {
    }

    public LogicRuntimeException(String message) {
        super(message);
    }

    public LogicRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogicRuntimeException(Throwable cause) {
        super(cause);
    }
}
