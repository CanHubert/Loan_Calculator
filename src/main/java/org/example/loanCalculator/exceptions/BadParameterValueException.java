package org.example.loanCalculator.exceptions;

public class BadParameterValueException extends RuntimeException {
    public BadParameterValueException() {
    }

    public BadParameterValueException(String message) {
        super(message);
    }

    public BadParameterValueException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadParameterValueException(Throwable cause) {
        super(cause);
    }
}
