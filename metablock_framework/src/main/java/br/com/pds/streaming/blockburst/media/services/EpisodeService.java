package br.com.pds.streaming.blockburst.media.services;

import br.com.pds.streaming.blockburst.media.repositories.EpisodeRepository;
import br.com.pds.streaming.blockburst.media.repositories.SeasonRepository;
import br.com.pds.streaming.framework.exceptions.EntityNotFoundException;
import br.com.pds.streaming.framework.exceptions.InvalidVideoException;
import br.com.pds.streaming.blockburst.mapper.modelMapper.BlockburstMapper;
import br.com.pds.streaming.blockburst.media.model.dto.EpisodeDTO;
import br.com.pds.streaming.blockburst.media.model.entities.Episode;
import br.com.pds.streaming.blockburst.media.model.entities.Season;
import br.com.pds.streaming.framework.cloud.services.CloudStorageService;
import br.com.pds.streaming.framework.exceptions.InvalidAnimationException;
import br.com.pds.streaming.framework.exceptions.InvalidThumbnailException;
import br.com.pds.streaming.framework.media.util.FileExtensionValidator;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EpisodeService {

    private final EpisodeRepository episodeRepository;
    private final BlockburstMapper mapper;
    private final CloudStorageService cloudStorageService;
    private final SeasonRepository seasonRepository;

    @Autowired // Tentar instanciar o MediaService com o BlockburstMapper dinamicamente
    public EpisodeService(EpisodeRepository episodeRepository, BlockburstMapper mapper, CloudStorageService cloudStorageService, SeasonRepository seasonRepository) {
        this.episodeRepository = episodeRepository;
        this.mapper = mapper;
        this.cloudStorageService = cloudStorageService;
        this.seasonRepository = seasonRepository;
    }

    public List<EpisodeDTO> findAll() {
        return mapper.convertList(episodeRepository.findAll(), EpisodeDTO.class);
    }

    public List<EpisodeDTO> findBySeasonId(String seasonId) {
        return mapper.convertList(episodeRepository.findBySeasonId(seasonId), EpisodeDTO.class);
    }

    public EpisodeDTO findById(String id) {
        return mapper.convertValue(episodeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Episode.class)), EpisodeDTO.class);
    }

    public EpisodeDTO insert(EpisodeDTO episodeDTO, String seasonId) {

        verifyFileUrl(episodeDTO);

        var episode = mapper.convertValue(episodeDTO, Episode.class);
        episode.setSeasonId(seasonId);
        var createdEpisode = episodeRepository.save(episode);

        var season = seasonRepository.findById(seasonId).orElseThrow(() -> new EntityNotFoundException(Season.class));
        season.getEpisodes().add(episode);
        seasonRepository.save(season);

        return mapper.convertValue(createdEpisode, EpisodeDTO.class);
    }

    public EpisodeDTO update(EpisodeDTO episodeDTO, String id) {

        verifyFileUrl(episodeDTO);

        var episode = episodeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Episode.class));

        episode.setTitle(episodeDTO.getTitle());
        episode.setDescription(episodeDTO.getDescription());
        episode.setVideoUrl(episodeDTO.getVideoUrl());
        episode.setThumbnailUrl(episodeDTO.getThumbnailUrl());
        episode.setAnimationUrl(episodeDTO.getAnimationUrl());

        var updatedEpisode = episodeRepository.save(episode);

        return mapper.convertValue(updatedEpisode, EpisodeDTO.class);
    }

    public EpisodeDTO patch(EpisodeDTO episodeDTO, String id) {

        var episode = episodeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Episode.class));

        if (episodeDTO.getTitle() != null) episode.setTitle(episodeDTO.getTitle());
        if (episodeDTO.getDescription() != null) episode.setDescription(episodeDTO.getDescription());

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

        var patchedEpisode = episodeRepository.save(episode);

        return mapper.convertValue(patchedEpisode, EpisodeDTO.class);
    }

    public void delete(String id) {

        var episode = findById(id);
        var movieVideo = episode.getVideoUrl();
        var movieThumbnail = episode.getThumbnailUrl();
        var movieAnimation = episode.getAnimationUrl();

        cloudStorageService.deleteFile(movieVideo);
        cloudStorageService.deleteFile(movieThumbnail);
        cloudStorageService.deleteFile(movieAnimation);

        episodeRepository.deleteById(id);
    }

    private void verifyFileUrl(@NotNull EpisodeDTO episodeDTO) {
        if (!FileExtensionValidator.validateVideoFileExtension(episodeDTO.getVideoUrl())) throw new InvalidVideoException(episodeDTO.getVideoUrl());
        if (!FileExtensionValidator.validateThumbnailFileExtension(episodeDTO.getThumbnailUrl())) throw new InvalidThumbnailException(episodeDTO.getThumbnailUrl());
        if (!FileExtensionValidator.validateAnimationFileExtension(episodeDTO.getAnimationUrl())) throw new InvalidAnimationException(episodeDTO.getAnimationUrl());
    }
}
