package br.com.pds.streaming.media.services;

import br.com.pds.streaming.cloud.services.CloudStorageService;
import br.com.pds.streaming.exceptions.InvalidAnimationException;
import br.com.pds.streaming.exceptions.InvalidSourceException;
import br.com.pds.streaming.exceptions.InvalidThumbnailException;
import br.com.pds.streaming.exceptions.EntityNotFoundException;
import br.com.pds.streaming.mapper.modelMapper.MyModelMapper;
import br.com.pds.streaming.media.model.dto.SeasonDTO;
import br.com.pds.streaming.media.model.entities.Season;
import br.com.pds.streaming.media.model.entities.TvShow;
import br.com.pds.streaming.media.repositories.EpisodeRepository;
import br.com.pds.streaming.media.repositories.SeasonRepository;
import br.com.pds.streaming.media.repositories.TvShowRepository;
import br.com.pds.streaming.media.util.FileExtensionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeasonService {

    private final SeasonRepository seasonRepository;
    private final TvShowRepository tvShowRepository;
    private final EpisodeRepository episodeRepository;
    private final MyModelMapper mapper;
    private final CloudStorageService cloudStorageService;

    @Autowired
    public SeasonService(SeasonRepository seasonRepository, TvShowRepository tvShowRepository, EpisodeRepository episodeRepository, MyModelMapper mapper, CloudStorageService cloudStorageService) {
        this.seasonRepository = seasonRepository;
        this.tvShowRepository = tvShowRepository;
        this.episodeRepository = episodeRepository;
        this.mapper = mapper;
        this.cloudStorageService = cloudStorageService;
    }

    public List<SeasonDTO> findAll() {

        var seasons = seasonRepository.findAll();

        return mapper.convertList(seasons, SeasonDTO.class);
    }

    public List<SeasonDTO> findByTvShowId(String tvShowId) {

        var seasons = seasonRepository.findByTvShowId(tvShowId);

        return mapper.convertList(seasons, SeasonDTO.class);
    }

    public SeasonDTO findById(String id) throws EntityNotFoundException {

        var season = seasonRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Season.class));

        return mapper.convertValue(season, SeasonDTO.class);
    }

    public SeasonDTO insert(SeasonDTO seasonDTO, String tvShowId) throws EntityNotFoundException, InvalidAnimationException, InvalidThumbnailException {

        verifyFilesUrl(seasonDTO);

        var tvShow = tvShowRepository.findById(tvShowId).orElseThrow(() -> new EntityNotFoundException(TvShow.class));

        Season season = mapper.convertValue(seasonDTO, Season.class);
        season.setTvShowId(tvShowId);
        season.setCategories(tvShow.getCategories());

        var createdSeason = seasonRepository.save(season);

        tvShow.getSeasons().add(createdSeason);

        tvShowRepository.save(tvShow);

        return mapper.convertValue(createdSeason, SeasonDTO.class);
    }

    public SeasonDTO update(SeasonDTO seasonDTO, String id) throws EntityNotFoundException, InvalidAnimationException, InvalidThumbnailException {

        verifyFilesUrl(seasonDTO);

        var season = seasonRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Season.class));

        season.setTitle(seasonDTO.getTitle());
        season.setDescription(seasonDTO.getDescription());
        season.setThumbnailUrl(seasonDTO.getThumbnailUrl());
        season.setAnimationUrl(seasonDTO.getAnimationUrl());
        season.setCategories(seasonDTO.getCategories());

        var updatedSeason = seasonRepository.save(season);

        return mapper.convertValue(updatedSeason, SeasonDTO.class);
    }

    public SeasonDTO patch(SeasonDTO seasonDTO, String id) throws EntityNotFoundException, InvalidAnimationException, InvalidThumbnailException {

        var season = seasonRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Season.class));

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

        var patchedSeason = seasonRepository.save(season);

        return mapper.convertValue(patchedSeason, SeasonDTO.class);
    }

    public void delete(String id) throws EntityNotFoundException, InvalidSourceException {

        deleteOrphanEpisodes(id);

        var season = findById(id);
        var movieThumb = season.getThumbnailUrl();
        var movieAnimation = season.getAnimationUrl();

        cloudStorageService.deleteFile(movieThumb);
        cloudStorageService.deleteFile(movieAnimation);

        seasonRepository.deleteById(id);
    }

    private void deleteOrphanEpisodes(String seasonId) {

        var episodes = episodeRepository.findBySeasonId(seasonId);

        episodeRepository.deleteAll(episodes);
    }

    private void verifyFilesUrl(SeasonDTO seasonDTO) throws InvalidThumbnailException, InvalidAnimationException {

        if (!FileExtensionValidator.validateThumbnailFileExtension(seasonDTO.getThumbnailUrl())) {
            throw new InvalidThumbnailException(seasonDTO.getThumbnailUrl());
        }

        if (!FileExtensionValidator.validateAnimationFileExtension(seasonDTO.getAnimationUrl())) {
            throw new InvalidAnimationException(seasonDTO.getAnimationUrl());
        }
    }
}
