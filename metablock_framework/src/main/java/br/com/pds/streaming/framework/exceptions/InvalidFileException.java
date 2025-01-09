package br.com.pds.streaming.framework.exceptions;

public class InvalidFileException extends Exception {

    public InvalidFileException(String fileName) {
        super(fileName + " is not a valid file.");
    }

    public InvalidFileException(String fileName, String fileType, String supportedExtensions) {
        super(fileName + " is not a valid " + fileType + ". Note that the supported extensions are " + supportedExtensions + ".");
    }
}
