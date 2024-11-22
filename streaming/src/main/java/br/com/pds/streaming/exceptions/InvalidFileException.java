package br.com.pds.streaming.exceptions;

import java.io.Serial;

public class InvalidFileException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidFileException(String fileName) {
        super(fileName + " is not a valid file.");
    }

    public InvalidFileException(String fileName, String fileType, String supportedExtensions) {
        super(fileName + " is not a valid " + fileType + ". Note that the supported extensions are " + supportedExtensions + ".");
    }

    public InvalidFileException(String fileName, Throwable cause) {
        super(fileName + " is not a valid file.", cause);
    }

    public InvalidFileException(String fileName, String fileType, String supportedExtensions, Throwable cause) {
        super(fileName + " is not a valid " + fileType + ". Note that the supported extensions are " + supportedExtensions + ".", cause);
    }
}
