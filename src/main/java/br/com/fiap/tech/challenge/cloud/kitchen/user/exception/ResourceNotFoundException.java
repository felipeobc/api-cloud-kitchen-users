package br.com.fiap.tech.challenge.cloud.kitchen.user.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
