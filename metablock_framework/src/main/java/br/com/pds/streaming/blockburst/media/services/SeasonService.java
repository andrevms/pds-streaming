package br.com.pds.streaming.blockburst.media.services;

import br.com.pds.streaming.blockburst.mapper.modelMapper.BlockburstMapper;
import br.com.pds.streaming.blockburst.media.model.dto.SeasonDTO;
import br.com.pds.streaming.blockburst.media.model.entities.Episode;
import br.com.pds.streaming.blockburst.media.model.entities.Season;
import br.com.pds.streaming.blockburst.media.model.entities.TvShow;
import br.com.pds.streaming.blockburst.repositories.EpisodeRepository;
import br.com.pds.streaming.blockburst.repositories.SeasonRepository;
import br.com.pds.streaming.blockburst.repositories.TvShowRepository;
import br.com.pds.streaming.framework.cloud.services.CloudStorageService;
import br.com.pds.streaming.framework.exceptions.EntityNotFoundException;
import br.com.pds.streaming.framework.exceptions.InvalidAnimationException;
import br.com.pds.streaming.framework.exceptions.InvalidThumbnailException;
import br.com.pds.streaming.framework.media.repositories.MediaRepository;
import br.com.pds.streaming.framework.media.util.FileExtensionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeasonService {

    private final SeasonRepository seasonRepository;
    private final TvShowRepository tvShowRepository;
    private final EpisodeService episodeService;
    private final BlockburstMapper mapper;
    private final CloudStorageService cloudStorageService;
    private final EpisodeRepository episodeRepository;

    @Autowired // Tentar instanciar o MediaService com o BlockburstMapper dinamicamente
    public SeasonService(SeasonRepository seasonRepository, TvShowRepository tvShowRepository, EpisodeService episodeService, BlockburstMapper mapper, CloudStorageService cloudStorageService, EpisodeRepository episodeRepository) {
        this.seasonRepository = seasonRepository;
        this.tvShowRepository = tvShowRepository;
        this.episodeService = episodeService;
        this.mapper = mapper;
        this.cloudStorageService = cloudStorageService;
        this.episodeRepository = episodeRepository;
    }

    public List<SeasonDTO> findAll() {
        return mapper.convertList(seasonRepository.findAll(), SeasonDTO.class);
    }

    public List<SeasonDTO> findByTvShowId(String tvShowId) {
        return mapper.convertList(seasonRepository.findByTvShowId(tvShowId), SeasonDTO.class);
    }

    public SeasonDTO findById(String id) {
        var season = seasonRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Season.class));

        return mapper.convertValue(season, SeasonDTO.class);
    }

    public SeasonDTO insert(SeasonDTO seasonDTO, String tvShowId) {

        verifyFilesUrl(seasonDTO);

        var season = mapper.convertValue(seasonDTO, Season.class);
        season.setTvShowId(tvShowId);
        var createdSeason = seasonRepository.save(season);

        var tvShow = tvShowRepository.findById(tvShowId).orElseThrow(() -> new EntityNotFoundException(TvShow.class));
        tvShow.getSeasons().add(season);
        tvShowRepository.save(tvShow);

        return mapper.convertValue(createdSeason, SeasonDTO.class);
    }

    public SeasonDTO update(SeasonDTO seasonDTO, String id) {

        verifyFilesUrl(seasonDTO);

        var season = seasonRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Season.class));

        season.setTitle(seasonDTO.getTitle());
        season.setDescription(seasonDTO.getDescription());
        season.setThumbnailUrl(seasonDTO.getThumbnailUrl());
        season.setAnimationUrl(seasonDTO.getAnimationUrl());

        var updatedSeason = seasonRepository.save(season);

        return mapper.convertValue(updatedSeason, SeasonDTO.class);
    }

    public SeasonDTO patch(SeasonDTO seasonDTO, String id) {

        var season = seasonRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Season.class));

        if (seasonDTO.getTitle() != null) season.setTitle(seasonDTO.getTitle());
        if (seasonDTO.getDescription() != null) season.setDescription(seasonDTO.getDescription());

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

        var patchedSeason = seasonRepository.save(season);

        return mapper.convertValue(patchedSeason, SeasonDTO.class);
    }

    public void delete(String id) {

        deleteOrphanEpisodes(id);

        var season = findById(id);
        var movieThumb = season.getThumbnailUrl();
        var movieAnimation = season.getAnimationUrl();

        cloudStorageService.deleteFile(movieThumb);
        cloudStorageService.deleteFile(movieAnimation);

        seasonRepository.deleteById(id);
    }

    protected void deleteOrphanEpisodes(String seasonId) {
        episodeRepository.deleteAll(mapper.convertList(episodeService.findBySeasonId(seasonId), Episode.class));
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
