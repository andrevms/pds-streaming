package br.com.pds.streaming.exceptions;

public class EntityNotFoundException extends Exception {

    public EntityNotFoundException(String entityName) {
        super(entityName + " not found.");
    }

    public EntityNotFoundException(Class<?> clazz) {
        super(clazz.getSimpleName() + " not found.");
    }
}
