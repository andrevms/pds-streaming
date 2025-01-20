package br.com.pds.streaming.blockburst.media.services;

import br.com.pds.streaming.blockburst.exceptions.InvalidVideoException;
import br.com.pds.streaming.blockburst.mapper.modelMapper.BlockburstMapper;
import br.com.pds.streaming.blockburst.media.model.dto.EpisodeDTO;
import br.com.pds.streaming.blockburst.media.model.entities.Episode;
import br.com.pds.streaming.blockburst.media.model.entities.Season;
import br.com.pds.streaming.framework.cloud.services.CloudStorageService;
import br.com.pds.streaming.framework.exceptions.InvalidAnimationException;
import br.com.pds.streaming.framework.exceptions.InvalidThumbnailException;
import br.com.pds.streaming.framework.media.services.MediaService;
import br.com.pds.streaming.framework.media.util.FileExtensionValidator;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EpisodeService {

    private final MediaService mediaService;
    private final BlockburstMapper mapper;
    private final CloudStorageService cloudStorageService;

    @Autowired // Tentar instanciar o MediaService com o BlockburstMapper dinamicamente
    public EpisodeService(MediaService mediaService, BlockburstMapper mapper, CloudStorageService cloudStorageService) {
        this.mediaService = mediaService;
        this.mapper = mapper;
        this.cloudStorageService = cloudStorageService;
    }

    public List<EpisodeDTO> findAll() {
        return mediaService.findAll(Episode.class, EpisodeDTO.class);
    }

    public List<EpisodeDTO> findBySeasonId(String seasonId) {
        return mapper.convertList(mediaService.findAll(Episode.class).stream().filter(e -> e.getSeasonId().equals(seasonId)).toList(), EpisodeDTO.class);
    }

    public EpisodeDTO findById(String id) {
        return mediaService.findById(id, Episode.class, EpisodeDTO.class);
    }

    public EpisodeDTO insert(EpisodeDTO episodeDTO, String seasonId) {

        verifyFilesUrl(episodeDTO);

        var episode = mapper.convertValue(episodeDTO, Episode.class);
        episode.setSeasonId(seasonId);
        var createdEpisode = mediaService.persist(episode);

        var season = mediaService.findById(seasonId, Season.class);
        season.getEpisodes().add(episode);
        mediaService.persist(season);

        return mapper.convertValue(createdEpisode, EpisodeDTO.class);
    }

    public EpisodeDTO update(EpisodeDTO episodeDTO, String id) {

        verifyFilesUrl(episodeDTO);

        var episode = mediaService.findById(id, Episode.class);

        episode.setTitle(episodeDTO.getTitle());
        episode.setDescription(episodeDTO.getDescription());
        episode.setVideoUrl(episodeDTO.getVideoUrl());
        episode.setThumbnailUrl(episodeDTO.getThumbnailUrl());
        episode.setAnimationUrl(episodeDTO.getAnimationUrl());

        var updatedEpisode = mediaService.persist(episode);

        return mapper.convertValue(updatedEpisode, EpisodeDTO.class);
    }

    public EpisodeDTO patch(EpisodeDTO episodeDTO, String id) {

        var episode = mediaService.findById(id, Episode.class);

        if (episodeDTO.getTitle() != null) {
            episode.setTitle(episodeDTO.getTitle());
        }

        if (episodeDTO.getDescription() != null) {
            episode.setDescription(episodeDTO.getDescription());
        }

        if (episodeDTO.getVideoUrl() != null) {

            if (!FileExtensionValidator.validateVideoFileExtension(episodeDTO.getVideoUrl())) {
                throw new InvalidVideoException(episodeDTO.getVideoUrl());
            }

            episode.setVideoUrl(episodeDTO.getVideoUrl());
        }

        if (episodeDTO.getThumbnailUrl() != null) {

            if (!FileExtensionValidator.validateThumbnailFileExtension(episodeDTO.getThumbnailUrl())) {
                throw new InvalidThumbnailException(episodeDTO.getThumbnailUrl());
            }

            episode.setThumbnailUrl(episodeDTO.getThumbnailUrl());
        }

        if (episodeDTO.getAnimationUrl() != null) {

            if (!FileExtensionValidator.validateAnimationFileExtension(episodeDTO.getAnimationUrl())) {
                throw new InvalidAnimationException(episodeDTO.getAnimationUrl());
            }

            episode.setAnimationUrl(episodeDTO.getAnimationUrl());
        }

        var patchedEpisode = mediaService.persist(episode);

        return mapper.convertValue(patchedEpisode, EpisodeDTO.class);
    }

    public void delete(String id) {

        var episode = findById(id);
        var movieSource = episode.getVideoUrl();
        var movieThumb = episode.getThumbnailUrl();
        var movieAnimation = episode.getAnimationUrl();

        cloudStorageService.deleteFile(movieSource);
        cloudStorageService.deleteFile(movieThumb);
        cloudStorageService.deleteFile(movieAnimation);

        mediaService.delete(id);
    }

    private void verifyFilesUrl(@NotNull EpisodeDTO episodeDTO) {

        if (!FileExtensionValidator.validateVideoFileExtension(episodeDTO.getVideoUrl())) {
            throw new InvalidVideoException(episodeDTO.getVideoUrl());
        }

        if (!FileExtensionValidator.validateThumbnailFileExtension(episodeDTO.getThumbnailUrl())) {
            throw new InvalidThumbnailException(episodeDTO.getThumbnailUrl());
        }

        if (!FileExtensionValidator.validateAnimationFileExtension(episodeDTO.getAnimationUrl())) {
            throw new InvalidAnimationException(episodeDTO.getAnimationUrl());
        }
    }
}
