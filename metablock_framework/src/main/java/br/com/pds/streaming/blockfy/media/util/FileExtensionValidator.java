package br.com.pds.streaming.blockfy.media.util;

import static br.com.pds.streaming.framework.media.util.FileExtensionValidator.validateFileExtension;

public class FileExtensionValidator {

    public static boolean validateAudioFileExtension(String filePath) {
        return validateFileExtension(".*\\.(mp3|wav|aiff|flac|alac|ape|aac|ogg|wma|mp4|opus|midi|dsd)$", filePath);
    }

    public static boolean validateVideoFileExtension(String filePath) {
        return validateFileExtension(".*\\.(mp4|avi|mkv|mov|flv|webm|wmv|mpg|mpeg|ts|ogv|3gp|f4v|m4v)$", filePath);
    }
}
