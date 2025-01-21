package br.com.pds.streaming.yulearn.exceptions;

import br.com.pds.streaming.framework.exceptions.InvalidFileException;

public class InvalidPdfException extends InvalidFileException {

    public InvalidPdfException(String fileName) {
        super(fileName, "pdf", "pdf");
    }
}
