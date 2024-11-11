package br.com.pds.streaming.exceptions;

import java.io.Serial;

public class ObjectNotFoundException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public ObjectNotFoundException(String message) {
        super(message);
    }

    public ObjectNotFoundException(Class<?> clazz) {
        super(clazz.getSimpleName() + " not found.");
    }

    public ObjectNotFoundException(Throwable cause) {
        super(cause);
    }

    public ObjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectNotFoundException(Class<?> clazz, Throwable cause) {
        super(clazz.getSimpleName() + " not found.", cause);
    }
}
