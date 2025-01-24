package br.com.pds.streaming.blockburst.media.util;

import static br.com.pds.streaming.framework.media.util.FileExtensionValidator.validateFileExtension;

public class FileExtensionValidator {

    public static boolean validateVideoFileExtension(String filePath) {
        return validateFileExtension(".*\\.(mp4|avi|mkv|mov|flv|webm|wmv|mpg|mpeg|ts|ogv|3gp|f4v|m4v)$", filePath);
    }
}
