package br.com.pds.streaming.exceptions;

import java.io.Serial;

public class InvalidThumbnailException extends InvalidFileException {

    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidThumbnailException(String fileName) {
        super(fileName, "thumbnail", ".jpg, .jpeg, .png, .gif, .bmp, .tiff, .webp, .heif, .heic and .svg");
    }

    public InvalidThumbnailException(String fileName, Throwable cause) {
        super(fileName, "thumbnail", ".jpg, .jpeg, .png, .gif, .bmp, .tiff, .webp, .heif, .heic and .svg", cause);
    }
}
