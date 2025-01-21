package br.com.pds.streaming.framework.media.util;

import br.com.pds.streaming.framework.exceptions.InvalidAnimationException;
import br.com.pds.streaming.framework.exceptions.InvalidThumbnailException;
import br.com.pds.streaming.framework.media.model.dto.MediaDTO;

public class VerifyHelper {

    public static void verifyThumbnailUrl(MediaDTO mediaDTO) {
        if (!FileExtensionValidator.validateThumbnailFileExtension(mediaDTO.getThumbnailUrl())) throw new InvalidThumbnailException(mediaDTO.getThumbnailUrl());
    }

    public static void verifyAnimationUrl(MediaDTO mediaDTO) {
        if (!FileExtensionValidator.validateAnimationFileExtension(mediaDTO.getAnimationUrl())) throw new InvalidAnimationException(mediaDTO.getAnimationUrl());
    }
}
