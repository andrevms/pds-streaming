package br.com.pds.streaming.blockburst.media.services;

import br.com.pds.streaming.blockburst.mapper.modelMapper.BlockburstMapper;
import br.com.pds.streaming.blockburst.media.model.dto.TvShowRequest;
import br.com.pds.streaming.blockburst.media.model.dto.TvShowResponse;
import br.com.pds.streaming.blockburst.media.model.entities.TvShow;
import br.com.pds.streaming.blockburst.media.repositories.EpisodeRepository;
import br.com.pds.streaming.blockburst.media.repositories.SeasonRepository;
import br.com.pds.streaming.blockburst.media.repositories.TvShowRepository;
import br.com.pds.streaming.framework.cloud.services.CloudStorageService;
import br.com.pds.streaming.framework.exceptions.EntityNotFoundException;
import br.com.pds.streaming.framework.exceptions.InvalidAnimationException;
import br.com.pds.streaming.framework.exceptions.InvalidSourceException;
import br.com.pds.streaming.framework.exceptions.InvalidThumbnailException;
import br.com.pds.streaming.framework.media.repositories.LikeRatingRepository;
import br.com.pds.streaming.framework.media.util.FileExtensionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TvShowService {

    private final TvShowRepository tvShowRepository;
    private final SeasonRepository seasonRepository;
    private final EpisodeRepository episodeRepository;
    private final LikeRatingRepository ratingRepository;
    private final BlockburstMapper mapper;
    private final CloudStorageService cloudStorageService;

    @Autowired
    public TvShowService(TvShowRepository tvShowRepository, SeasonRepository seasonRepository, EpisodeRepository episodeRepository, LikeRatingRepository ratingRepository, BlockburstMapper mapper, CloudStorageService cloudStorageService) {
        this.tvShowRepository = tvShowRepository;
        this.seasonRepository = seasonRepository;
        this.episodeRepository = episodeRepository;
        this.ratingRepository = ratingRepository;
        this.mapper = mapper;
        this.cloudStorageService = cloudStorageService;
    }

    public List<TvShowResponse> findAll() {
        return mapper.convertList(tvShowRepository.findAll(), TvShowResponse.class);
    }

    public TvShowResponse findById(String id) throws EntityNotFoundException {

        var tvShow = tvShowRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(TvShow.class));

        return mapper.convertValue(tvShow, TvShowResponse.class);
    }

    public TvShowResponse insert(TvShowRequest tvShowRequest) throws InvalidThumbnailException, InvalidAnimationException {

        verifyFilesUrl(tvShowRequest);

        var createdTvShow = tvShowRepository.save(mapper.convertValue(tvShowRequest, TvShow.class));

        return mapper.convertValue(createdTvShow, TvShowResponse.class);
    }

    public TvShowResponse update(TvShowRequest tvShowRequest, String id) throws EntityNotFoundException, InvalidThumbnailException, InvalidAnimationException {

        verifyFilesUrl(tvShowRequest);

        var tvShow = tvShowRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(TvShow.class));

        tvShow.setTitle(tvShowRequest.getTitle());
        tvShow.setDescription(tvShowRequest.getDescription());
        tvShow.setThumbnailUrl(tvShowRequest.getThumbnailUrl());
        tvShow.setAnimationUrl(tvShowRequest.getAnimationUrl());
        tvShow.setCategories(tvShow.getCategories());

        var updatedTvShow = tvShowRepository.save(tvShow);

        return mapper.convertValue(updatedTvShow, TvShowResponse.class);
    }

    public TvShowResponse patch(TvShowRequest tvShowRequest, String id) throws EntityNotFoundException, InvalidThumbnailException, InvalidAnimationException {

        var tvShow = tvShowRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(TvShow.class));

        if (tvShowRequest.getTitle() != null) {
            tvShow.setTitle(tvShowRequest.getTitle());
        }

        if (tvShowRequest.getDescription() != null) {
            tvShow.setDescription(tvShowRequest.getDescription());
        }

        if (tvShowRequest.getThumbnailUrl() != null) {

            if (!FileExtensionValidator.validateThumbnailFileExtension(tvShowRequest.getThumbnailUrl())) {
                throw new InvalidThumbnailException(tvShowRequest.getThumbnailUrl());
            }

            tvShow.setThumbnailUrl(tvShowRequest.getThumbnailUrl());
        }

        if (tvShowRequest.getAnimationUrl() != null) {

            if (!FileExtensionValidator.validateAnimationFileExtension(tvShowRequest.getAnimationUrl())) {
                throw new InvalidAnimationException(tvShowRequest.getAnimationUrl());
            }

            tvShow.setAnimationUrl(tvShowRequest.getAnimationUrl());
        }

        var patchedTvShow = tvShowRepository.save(tvShow);

        return mapper.convertValue(patchedTvShow, TvShowResponse.class);
    }

    public void delete(String id) throws EntityNotFoundException, InvalidSourceException {

        deleteOrphanSeasons(id);
        deleteOrphanRatings(id);

        var tvShow = findById(id);
        var movieThumb = tvShow.getThumbnailUrl();
        var movieAnimation = tvShow.getAnimationUrl();

        cloudStorageService.deleteFile(movieThumb);
        cloudStorageService.deleteFile(movieAnimation);

        tvShowRepository.deleteById(id);
    }

    private void deleteOrphanSeasons(String tvShowId) {

        var seasons = seasonRepository.findByTvShowId(tvShowId);

        seasons.forEach(season -> deleteOrphanEpisodes(season.getId()));

        seasonRepository.deleteAll(seasons);
    }

    private void deleteOrphanEpisodes(String seasonId) {

        var episodes = episodeRepository.findBySeasonId(seasonId);

        episodeRepository.deleteAll(episodes);
    }

    private void deleteOrphanRatings(String tvShowId) throws EntityNotFoundException {

        var tvShow = tvShowRepository.findById(tvShowId).orElseThrow(() -> new EntityNotFoundException(TvShow.class));

        ratingRepository.deleteAll(ratingRepository.findAll().stream().filter(r -> tvShow.getRatings().contains(r)).toList());
    }

    private void verifyFilesUrl(TvShowRequest tvShowRequest) throws InvalidThumbnailException, InvalidAnimationException {

        if (!FileExtensionValidator.validateThumbnailFileExtension(tvShowRequest.getThumbnailUrl())) {
            throw new InvalidThumbnailException(tvShowRequest.getThumbnailUrl());
        }

        if (!FileExtensionValidator.validateAnimationFileExtension(tvShowRequest.getAnimationUrl())) {
            throw new InvalidAnimationException(tvShowRequest.getAnimationUrl());
        }
    }
}
