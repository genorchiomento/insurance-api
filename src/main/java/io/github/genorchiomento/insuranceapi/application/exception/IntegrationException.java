package io.github.genorchiomento.insuranceapi.application.exception;

public class IntegrationException extends RuntimeException {
    public IntegrationException(String message, Throwable cause) {
        super(message, cause);
    }
}