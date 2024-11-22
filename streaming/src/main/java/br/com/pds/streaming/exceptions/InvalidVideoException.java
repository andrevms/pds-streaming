package br.com.pds.streaming.exceptions;

import java.io.Serial;

public class InvalidVideoException extends InvalidFileException {

    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidVideoException(String fileName) {
        super(fileName, "video", ".mp4, .avi, .mkv, .mov, .flv, .webm, .wmv, .mpg, .mpeg, .ts, .ogv, .3gp, .f4v and .m4v");
    }

    public InvalidVideoException(String fileName, Throwable cause) {
        super(fileName, "video", ".mp4, .avi, .mkv, .mov, .flv, .webm, .wmv, .mpg, .mpeg, .ts, .ogv, .3gp, .f4v and .m4v", cause);
    }
}
