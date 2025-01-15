package br.com.pds.streaming.framework.exceptions;

public class InvalidSubscriptionTypeException extends RuntimeException {

    public InvalidSubscriptionTypeException(String subscriptionType) {
        super("Invalid subscription type: " + subscriptionType);
    }
}
