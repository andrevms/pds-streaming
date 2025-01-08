package br.com.pds.streaming.exceptions;

public class InvalidThumbnailException extends InvalidFileException {

    public InvalidThumbnailException(String fileName) {
        super(fileName, "thumbnail", ".jpg, .jpeg, .png, .gif, .bmp, .tiff, .webp, .heif, .heic and .svg");
    }
}
