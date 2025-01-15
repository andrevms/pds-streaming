package br.com.pds.streaming.blockburst.media.services;

import br.com.pds.streaming.blockburst.mapper.modelMapper.BlockburstMapper;
import br.com.pds.streaming.blockburst.media.model.dto.SeasonDTO;
import br.com.pds.streaming.blockburst.media.model.entities.Episode;
import br.com.pds.streaming.blockburst.media.model.entities.Season;
import br.com.pds.streaming.blockburst.media.model.entities.TvShow;
import br.com.pds.streaming.framework.cloud.services.CloudStorageService;
import br.com.pds.streaming.framework.exceptions.InvalidAnimationException;
import br.com.pds.streaming.framework.exceptions.InvalidThumbnailException;
import br.com.pds.streaming.framework.media.services.MediaService;
import br.com.pds.streaming.framework.media.util.FileExtensionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeasonService {

    private final MediaService mediaService;
    private final EpisodeService episodeService;
    private final BlockburstMapper mapper;
    private final CloudStorageService cloudStorageService;

    @Autowired // Tentar instanciar o MediaService com o BlockburstMapper dinamicamente
    public SeasonService(MediaService mediaService, EpisodeService episodeService, BlockburstMapper mapper, CloudStorageService cloudStorageService) {
        this.mediaService = mediaService;
        this.episodeService = episodeService;
        this.mapper = mapper;
        this.cloudStorageService = cloudStorageService;
    }

    public List<SeasonDTO> findAll() {
        return mediaService.findAll(Season.class, SeasonDTO.class);
    }

    public List<SeasonDTO> findByTvShowId(String tvShowId) {
        return mapper.convertList(mediaService.findAll(Season.class).stream().filter(s -> s.getTvShowId().equals(tvShowId)).toList(), SeasonDTO.class);
    }

    public SeasonDTO findById(String id) {
        return mediaService.findById(id, Season.class, SeasonDTO.class);
    }

    public SeasonDTO insert(SeasonDTO seasonDTO, String tvShowId) {

        verifyFilesUrl(seasonDTO);

        var season = mapper.convertValue(seasonDTO, Season.class);
        season.setTvShowId(tvShowId);
        var createdSeason = mediaService.persist(season);

        var tvShow = mediaService.findById(tvShowId, TvShow.class);
        tvShow.getSeasons().add(season);
        mediaService.persist(tvShow);

        return mapper.convertValue(createdSeason, SeasonDTO.class);
    }

    public SeasonDTO update(SeasonDTO seasonDTO, String id) {

        verifyFilesUrl(seasonDTO);

        var season = mediaService.findById(id, Season.class);

        season.setTitle(seasonDTO.getTitle());
        season.setDescription(seasonDTO.getDescription());
        season.setThumbnailUrl(seasonDTO.getThumbnailUrl());
        season.setAnimationUrl(seasonDTO.getAnimationUrl());

        var updatedSeason = mediaService.persist(season);

        return mapper.convertValue(updatedSeason, SeasonDTO.class);
    }

    public SeasonDTO patch(SeasonDTO seasonDTO, String id) {

        var season = mediaService.findById(id, Season.class);

        if (seasonDTO.getTitle() != null) {
            season.setTitle(seasonDTO.getTitle());
        }

        if (seasonDTO.getDescription() != null) {
            season.setDescription(seasonDTO.getDescription());
        }

        if (seasonDTO.getThumbnailUrl() != null) {

            if (!FileExtensionValidator.validateThumbnailFileExtension(seasonDTO.getThumbnailUrl())) {
                throw new InvalidThumbnailException(seasonDTO.getThumbnailUrl());
            }

            season.setThumbnailUrl(seasonDTO.getThumbnailUrl());
        }

        if (seasonDTO.getAnimationUrl() != null) {

            if (!FileExtensionValidator.validateAnimationFileExtension(seasonDTO.getAnimationUrl())) {
                throw new InvalidAnimationException(seasonDTO.getAnimationUrl());
            }

            season.setAnimationUrl(seasonDTO.getAnimationUrl());
        }

        var patchedSeason = mediaService.persist(season);

        return mapper.convertValue(patchedSeason, SeasonDTO.class);
    }

    public void delete(String id) {

        deleteOrphanEpisodes(id);

        var season = findById(id);
        var movieThumb = season.getThumbnailUrl();
        var movieAnimation = season.getAnimationUrl();

        cloudStorageService.deleteFile(movieThumb);
        cloudStorageService.deleteFile(movieAnimation);

        mediaService.delete(id);
    }

    protected void deleteOrphanEpisodes(String seasonId) {
        mediaService.delete(mapper.convertList(episodeService.findBySeasonId(seasonId), Episode.class));
    }

    private void verifyFilesUrl(SeasonDTO seasonDTO) {

        if (!FileExtensionValidator.validateThumbnailFileExtension(seasonDTO.getThumbnailUrl())) {
            throw new InvalidThumbnailException(seasonDTO.getThumbnailUrl());
        }

        if (!FileExtensionValidator.validateAnimationFileExtension(seasonDTO.getAnimationUrl())) {
            throw new InvalidAnimationException(seasonDTO.getAnimationUrl());
        }
    }
}
