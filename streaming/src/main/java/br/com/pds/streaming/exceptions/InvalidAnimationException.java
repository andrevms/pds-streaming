package br.com.pds.streaming.exceptions;

import java.io.Serial;

public class InvalidAnimationException extends InvalidFileException {

    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidAnimationException(String fileName) {
        super(fileName, "animation", ".gif and .webp");
    }

    public InvalidAnimationException(String fileName, Throwable cause) {
        super(fileName, "animation", ".gif and .webp", cause);
    }
}
