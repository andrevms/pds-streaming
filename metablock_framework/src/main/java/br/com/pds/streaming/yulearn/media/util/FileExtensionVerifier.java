package br.com.pds.streaming.yulearn.media.util;

import br.com.pds.streaming.yulearn.exceptions.InvalidPdfException;
import br.com.pds.streaming.yulearn.exceptions.InvalidVideoException;
import br.com.pds.streaming.yulearn.media.model.dto.TextLessonRequest;
import br.com.pds.streaming.yulearn.media.model.dto.VideoLessonRequest;

public class FileExtensionVerifier {

    public static void verifyVideoUrl(VideoLessonRequest videoLessonRequest) {
        if (!FileExtensionValidator.validateVideoFileExtension(videoLessonRequest.getVideoUrl())) throw new InvalidVideoException(videoLessonRequest.getVideoUrl());
    }

    public static void verifyPdfUrl(TextLessonRequest textLessonRequest) {
        if (!FileExtensionValidator.validatePdfFileExtension(textLessonRequest.getPdfUrl())) throw new InvalidPdfException(textLessonRequest.getPdfUrl());
    }
}
