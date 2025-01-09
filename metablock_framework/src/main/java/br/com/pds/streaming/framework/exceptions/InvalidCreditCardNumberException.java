package br.com.pds.streaming.framework.exceptions;

public class InvalidCreditCardNumberException extends Exception {

    public InvalidCreditCardNumberException() {
        super("The credit number is invalid.");
    }
}
