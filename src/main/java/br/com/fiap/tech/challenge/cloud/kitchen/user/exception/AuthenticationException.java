package br.com.fiap.tech.challenge.cloud.kitchen.user.exception;

public class AuthenticationException extends RuntimeException {

    public AuthenticationException(String message) {
        super(message);
    }
}
