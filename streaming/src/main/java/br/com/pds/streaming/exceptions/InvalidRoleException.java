package br.com.pds.streaming.exceptions;

public class InvalidRoleException extends Exception {

    public InvalidRoleException(String message) {
        super("Invalid role: " + message);
    }
}
