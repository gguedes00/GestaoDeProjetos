package br.com.gestaoProjeto.Exception;

public class InvalidStatusTransitionException extends Exception {
    public InvalidStatusTransitionException(String message) {
        super(message);
    }
}