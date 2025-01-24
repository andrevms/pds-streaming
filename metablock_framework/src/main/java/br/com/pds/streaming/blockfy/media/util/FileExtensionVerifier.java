package br.com.pds.streaming.blockfy.media.util;

import br.com.pds.streaming.blockfy.exceptions.InvalidAudioException;
import br.com.pds.streaming.blockfy.exceptions.InvalidVideoException;
import br.com.pds.streaming.blockfy.media.model.dto.AudioDTO;
import br.com.pds.streaming.blockfy.media.model.dto.PodcastDTO;

import static br.com.pds.streaming.framework.media.util.FileExtensionVerifier.*;

public class FileExtensionVerifier {

    public static void verifyAudioUrl(AudioDTO audioDTO) {
        if (!FileExtensionValidator.validateAudioFileExtension(audioDTO.getAudioUrl())) throw new InvalidAudioException(audioDTO.getAudioUrl());
    }

    public static void verifyVideoUrl(PodcastDTO podcastDTO) {
        if (!FileExtensionValidator.validateVideoFileExtension(podcastDTO.getVideoUrl())) throw new InvalidVideoException(podcastDTO.getVideoUrl());
    }

    public static void verifyFileUrl(AudioDTO audioDTO) {
        verifyAudioUrl(audioDTO);
        verifyThumbnailUrl(audioDTO);
        verifyAnimationUrl(audioDTO);
    }

    public static void verifyFileUrl(PodcastDTO podcastDTO) {
        verifyVideoUrl(podcastDTO);
        verifyFileUrl((AudioDTO) podcastDTO);
    }
}
