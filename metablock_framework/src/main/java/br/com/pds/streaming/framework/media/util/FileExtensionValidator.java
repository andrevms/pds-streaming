package br.com.pds.streaming.framework.media.util;

import java.util.regex.Pattern;

public class FileExtensionValidator {

    public static boolean validateThumbnailFileExtension(String filePath) {
        return Pattern.compile(".*\\.(jpg|jpeg|png|gif|bmp|tiff|webp|heif|heic|svg)$", Pattern.CASE_INSENSITIVE).matcher(filePath).matches();
    }

    public static boolean validateAnimationFileExtension(String filePath) {
        return Pattern.compile(".*\\.(gif|webp|heic)$", Pattern.CASE_INSENSITIVE).matcher(filePath).matches();
    }

    public static boolean validateVideoFileExtension(String filePath) {
        return Pattern.compile(".*\\.(mp4|avi|mkv|mov|flv|webm|wmv|mpg|mpeg|ts|ogv|3gp|f4v|m4v)$", Pattern.CASE_INSENSITIVE).matcher(filePath).matches();
    }
}
