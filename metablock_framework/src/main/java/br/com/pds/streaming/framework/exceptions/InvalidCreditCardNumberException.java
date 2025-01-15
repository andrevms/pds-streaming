package br.com.pds.streaming.framework.exceptions;

public class InvalidCreditCardNumberException extends RuntimeException {

    public InvalidCreditCardNumberException() {
        super("The credit number is invalid.");
    }
}
