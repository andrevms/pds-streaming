package br.com.pds.streaming.exceptions;

public class InvalidSubscriptionTypeException extends Exception {

    public InvalidSubscriptionTypeException(String subscriptionType) {
        super("Invalid subscription type: " + subscriptionType);
    }
}
