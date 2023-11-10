package br.com.crypto_dashboard.infra.exception;

public class UserDoesNotOwnResourceException extends RuntimeException {
    public UserDoesNotOwnResourceException(String message) {
        super(message);
    }

    public UserDoesNotOwnResourceException() {
    }
}
