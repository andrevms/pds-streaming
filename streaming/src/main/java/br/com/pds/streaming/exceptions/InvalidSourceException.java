package br.com.pds.streaming.exceptions;

import java.io.Serial;

public class InvalidSourceException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidSourceException(String message) {
        super(message);
    }
}
