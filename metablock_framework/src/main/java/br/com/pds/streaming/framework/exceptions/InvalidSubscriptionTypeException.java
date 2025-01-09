package br.com.pds.streaming.framework.exceptions;

public class InvalidSubscriptionTypeException extends Exception {

    public InvalidSubscriptionTypeException(String subscriptionType) {
        super("Invalid subscription type: " + subscriptionType);
    }
}
