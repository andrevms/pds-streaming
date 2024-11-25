package br.com.pds.streaming.exceptions;

public class InvalidAnimationException extends InvalidFileException {

    public InvalidAnimationException(String fileName) {
        super(fileName, "animation", ".gif, .webp and .heic");
    }
}
