package br.com.pds.streaming.exceptions;

public class InvalidCreditCardNumberException extends Exception {

    public InvalidCreditCardNumberException() {
        super("The credit number is invalid.");
    }
}
