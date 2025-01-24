package br.com.pds.streaming.blockburst.media.util;

import br.com.pds.streaming.blockburst.exceptions.InvalidVideoException;
import br.com.pds.streaming.blockburst.media.model.dto.EpisodeDTO;
import br.com.pds.streaming.blockburst.media.model.dto.MovieRequest;

public class FileExtensionVerifier {

    public static void verifyVideoUrl(EpisodeDTO episodeDTO) {
        if (!FileExtensionValidator.validateVideoFileExtension(episodeDTO.getVideoUrl())) throw new InvalidVideoException(episodeDTO.getVideoUrl());
    }

    public static void verifyVideoUrl(MovieRequest movieRequest) {
        if (!FileExtensionValidator.validateVideoFileExtension(movieRequest.getVideoUrl())) throw new InvalidVideoException(movieRequest.getVideoUrl());
    }
}
