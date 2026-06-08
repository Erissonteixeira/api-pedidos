package io.github.Erissonteixeira.api_pedidos.exception; // pacote de tratamento de erros

public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}