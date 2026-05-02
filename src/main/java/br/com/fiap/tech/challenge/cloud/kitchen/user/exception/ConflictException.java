package br.com.fiap.tech.challenge.cloud.kitchen.user.exception;

public class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
    }
}
