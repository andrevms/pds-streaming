package br.com.pds.streaming.exceptions;

public class EntityNotFoundException extends Exception {

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(Class<?> clazz) {
        super(clazz.getSimpleName() + " not found.");
    }
}
