package br.com.pds.streaming.blockfy.media.services;

import br.com.pds.streaming.blockfy.exceptions.InvalidAudioException;
import br.com.pds.streaming.blockfy.media.model.dto.AudioDTO;
import br.com.pds.streaming.blockfy.media.model.dto.PodcastDTO;
import br.com.pds.streaming.framework.exceptions.InvalidAnimationException;
import br.com.pds.streaming.framework.exceptions.InvalidThumbnailException;
import br.com.pds.streaming.framework.exceptions.InvalidVideoException;
import br.com.pds.streaming.framework.media.util.FileExtensionValidator;

public class AudioService {

    protected static void verifyFileAudioUrl(AudioDTO audioDTO) {
        if (!FileExtensionValidator.validateAudioFileExtension(audioDTO.getAudioUrl())) {
            throw new InvalidAudioException(audioDTO.getAudioUrl());
        }
    }

    protected static void verifyFileThumbnailUrl(AudioDTO audioDTO) {
        if (!FileExtensionValidator.validateThumbnailFileExtension(audioDTO.getThumbnailUrl())) {
            throw new InvalidThumbnailException(audioDTO.getThumbnailUrl());
        }
    }

    protected static void verifyFileAnimationUrl(AudioDTO audioDTO) {
        if (!FileExtensionValidator.validateAnimationFileExtension(audioDTO.getAnimationUrl())) {
            throw new InvalidAnimationException(audioDTO.getAnimationUrl());
        }
    }

    protected static void verifyFileVideoUrl(PodcastDTO podcastDTO) {
        if (podcastDTO.getVideoUrl() != null) {
            if (!FileExtensionValidator.validateVideoFileExtension(podcastDTO.getVideoUrl())) {
                throw new InvalidVideoException(podcastDTO.getVideoUrl());
            }
        }
    }

    protected static void verifyFileUrl(AudioDTO audioDTO) {
        verifyFileAudioUrl(audioDTO);
        verifyFileThumbnailUrl(audioDTO);
        verifyFileAnimationUrl(audioDTO);
    }

    protected static void verifyFileUrl(PodcastDTO podcastDTO) {
        verifyFileVideoUrl(podcastDTO);
        verifyFileUrl((AudioDTO) podcastDTO);
    }
}
