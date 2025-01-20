package br.com.pds.streaming.blockburst.media.services;

import br.com.pds.streaming.blockburst.mapper.modelMapper.BlockburstMapper;
import br.com.pds.streaming.blockburst.media.model.dto.TvShowRequest;
import br.com.pds.streaming.blockburst.media.model.dto.TvShowResponse;
import br.com.pds.streaming.blockburst.media.model.entities.Season;
import br.com.pds.streaming.blockburst.media.model.entities.TvShow;
import br.com.pds.streaming.framework.cloud.services.CloudStorageService;
import br.com.pds.streaming.framework.exceptions.InvalidAnimationException;
import br.com.pds.streaming.framework.exceptions.InvalidThumbnailException;
import br.com.pds.streaming.framework.media.model.dto.MediaDTO;
import br.com.pds.streaming.framework.media.repositories.LikeRatingRepository;
import br.com.pds.streaming.framework.media.services.MediaService;
import br.com.pds.streaming.framework.media.util.FileExtensionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TvShowService {

    private final MediaService mediaService;
    private final SeasonService seasonService;
    private final LikeRatingRepository ratingRepository;
    private final BlockburstMapper mapper;
    private final CloudStorageService cloudStorageService;

    @Autowired // Tentar instanciar o MediaService com o BlockburstMapper dinamicamente
    public TvShowService(MediaService mediaService, SeasonService seasonService, LikeRatingRepository ratingRepository, BlockburstMapper mapper, CloudStorageService cloudStorageService) {
        this.mediaService = mediaService;
        this.seasonService = seasonService;
        this.ratingRepository = ratingRepository;
        this.mapper = mapper;
        this.cloudStorageService = cloudStorageService;
    }

    public List<TvShowResponse> findAll() {
        return mediaService.findAll(TvShow.class, TvShowResponse.class);
    }

    public TvShowResponse findById(String id) {
        return mediaService.findById(id, TvShow.class, TvShowResponse.class);
    }

    public TvShowResponse insert(TvShowRequest tvShowRequest) {

        verifyFilesUrl(tvShowRequest);

        return mediaService.persist(tvShowRequest, TvShow.class, TvShowResponse.class);
    }

    public TvShowResponse update(TvShowRequest tvShowRequest, String id) {

        verifyFilesUrl(tvShowRequest);

        var tvShow = mediaService.findById(id, TvShow.class);

        tvShow.setTitle(tvShowRequest.getTitle());
        tvShow.setDescription(tvShowRequest.getDescription());
        tvShow.setThumbnailUrl(tvShowRequest.getThumbnailUrl());
        tvShow.setAnimationUrl(tvShowRequest.getAnimationUrl());
        tvShow.setCategories(tvShow.getCategories());

        var updatedTvShow = mediaService.persist(tvShow);

        return mapper.convertValue(updatedTvShow, TvShowResponse.class);
    }

    public TvShowResponse patch(TvShowRequest tvShowRequest, String id) {

        var tvShow = mediaService.findById(id, TvShow.class);

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

        var patchedTvShow = mediaService.persist(tvShow);

        return mapper.convertValue(patchedTvShow, TvShowResponse.class);
    }

    public void delete(String id) {

        deleteOrphanSeasons(id);
        deleteOrphanRatings(id);

        var tvShow = findById(id);
        var movieThumb = tvShow.getThumbnailUrl();
        var movieAnimation = tvShow.getAnimationUrl();

        cloudStorageService.deleteFile(movieThumb);
        cloudStorageService.deleteFile(movieAnimation);

        mediaService.delete(id);
    }

    protected void deleteOrphanSeasons(String tvShowId) {

        var seasons = seasonService.findByTvShowId(tvShowId);

        seasons.forEach(season -> seasonService.deleteOrphanEpisodes(season.getId()));

        mediaService.delete(mapper.convertList(seasons, Season.class));
    }

    protected void deleteOrphanRatings(String tvShowId) {

        var tvShow = mediaService.findById(tvShowId, TvShow.class);

        ratingRepository.deleteAll(ratingRepository.findAll().stream().filter(r -> tvShow.getRatings().contains(r)).toList());
    }

    private void verifyFilesUrl(TvShowRequest tvShowRequest) {

        if (!FileExtensionValidator.validateThumbnailFileExtension(tvShowRequest.getThumbnailUrl())) {
            throw new InvalidThumbnailException(tvShowRequest.getThumbnailUrl());
        }

        if (!FileExtensionValidator.validateAnimationFileExtension(tvShowRequest.getAnimationUrl())) {
            throw new InvalidAnimationException(tvShowRequest.getAnimationUrl());
        }
    }
}
