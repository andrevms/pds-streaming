package br.com.pds.streaming.framework.exceptions;

public class InvalidRoleException extends RuntimeException {

    public InvalidRoleException(String message) {
        super("Invalid role: " + message);
    }
}
