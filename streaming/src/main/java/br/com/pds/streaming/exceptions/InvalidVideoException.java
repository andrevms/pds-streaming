package br.com.pds.streaming.exceptions;

import java.io.Serial;

public class InvalidVideoException extends InvalidFileException {

    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidVideoException(String fileName) {
        super(fileName, "video", ".mkv, .mp4, .avi and .vob");
    }

    public InvalidVideoException(String fileName, Throwable cause) {
        super(fileName, "video", ".mkv, .mp4, .avi and .vob", cause);
    }
}
