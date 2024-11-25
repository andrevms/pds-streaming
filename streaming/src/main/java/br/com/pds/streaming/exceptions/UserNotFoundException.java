package br.com.pds.streaming.exceptions;

public class UserNotFoundException extends ObjectNotFoundException {

    public UserNotFoundException() {
        super("User not found.");
    }
}
