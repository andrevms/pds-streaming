package br.com.pds.streaming.framework.exceptions;

public class InvalidVideoException extends InvalidFileException {

    public InvalidVideoException(String fileName) {
        super(fileName, "video", ".mp4, .avi, .mkv, .mov, .flv, .webm, .wmv, .mpg, .mpeg, .ts, .ogv, .3gp, .f4v and .m4v");
    }
}
