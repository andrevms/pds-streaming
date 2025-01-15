package br.com.pds.streaming.framework.exceptions;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String entityName) {
        super(entityName + " not found.");
    }

    public EntityNotFoundException(Class<?> clazz) {
        super(clazz.getSimpleName() + " not found.");
    }
}
