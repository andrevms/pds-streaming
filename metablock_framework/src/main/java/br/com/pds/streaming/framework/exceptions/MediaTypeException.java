package br.com.pds.streaming.framework.exceptions;

public class MediaTypeException extends RuntimeException {

    public MediaTypeException() {
        super("This media type shouldn't be here.");
    }

    public MediaTypeException(Throwable cause) {
        super("This media type shouldn't be here.", cause);
    }
}
