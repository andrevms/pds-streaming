package br.com.pds.streaming.exceptions;

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException() {
        super("User not found.");
    }
}
