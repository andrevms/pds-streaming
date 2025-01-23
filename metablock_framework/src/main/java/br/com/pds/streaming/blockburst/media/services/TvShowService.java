package br.com.pds.streaming.blockburst.media.services;

import br.com.pds.streaming.blockburst.mapper.modelMapper.BlockburstMapper;
import br.com.pds.streaming.blockburst.media.model.dto.TvShowRequest;
import br.com.pds.streaming.blockburst.media.model.dto.TvShowResponse;
import br.com.pds.streaming.blockburst.media.model.entities.Season;
import br.com.pds.streaming.blockburst.media.model.entities.TvShow;
import br.com.pds.streaming.blockburst.media.repositories.SeasonRepository;
import br.com.pds.streaming.blockburst.media.repositories.TvShowRepository;
import br.com.pds.streaming.framework.cloud.services.CloudStorageService;
import br.com.pds.streaming.framework.exceptions.EntityNotFoundException;
import br.com.pds.streaming.framework.exceptions.InvalidAnimationException;
import br.com.pds.streaming.framework.exceptions.InvalidThumbnailException;
import br.com.pds.streaming.framework.media.repositories.LikeRatingRepository;
import br.com.pds.streaming.framework.media.util.FileExtensionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.pds.streaming.framework.media.util.VerifyHelper.*;

@Service
public class TvShowService {

    private final TvShowRepository tvShowRepository;
    private final SeasonService seasonService;
    private final LikeRatingRepository ratingRepository;
    private final BlockburstMapper mapper;
    private final CloudStorageService cloudStorageService;
    private final SeasonRepository seasonRepository;

    @Autowired
    public TvShowService(TvShowRepository tvShowRepository, SeasonService seasonService, LikeRatingRepository ratingRepository, BlockburstMapper mapper, CloudStorageService cloudStorageService, SeasonRepository seasonRepository) {
        this.tvShowRepository = tvShowRepository;
        this.seasonService = seasonService;
        this.ratingRepository = ratingRepository;
        this.mapper = mapper;
        this.cloudStorageService = cloudStorageService;
        this.seasonRepository = seasonRepository;
    }

    public List<TvShowResponse> findAll() {
        return mapper.convertList(tvShowRepository.findAll(), TvShowResponse.class);
    }

    public TvShowResponse findById(String id) {
        return mapper.convertValue(tvShowRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(TvShow.class)), TvShowResponse.class);
    }

    public TvShowResponse insert(TvShowRequest tvShowRequest) {

        verifyFileUrl(tvShowRequest);

        var tvShow = mapper.convertValue(tvShowRequest, TvShow.class);

        var createdTvShow = tvShowRepository.save(tvShow);

        return mapper.convertValue(createdTvShow, TvShowResponse.class);
    }

    public TvShowResponse update(TvShowRequest tvShowRequest, String id) throws Throwable {

        verifyFileUrl(tvShowRequest);

        TvShow tvShow = tvShowRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(TvShow.class));

        tvShow.setTitle(tvShowRequest.getTitle());
        tvShow.setDescription(tvShowRequest.getDescription());
        tvShow.setThumbnailUrl(tvShowRequest.getThumbnailUrl());
        tvShow.setAnimationUrl(tvShowRequest.getAnimationUrl());
        tvShow.setCategories(tvShow.getCategories());

        var updatedTvShow = tvShowRepository.save(tvShow);

        return mapper.convertValue(updatedTvShow, TvShowResponse.class);
    }

    public TvShowResponse patch(TvShowRequest tvShowRequest, String id) {

        var tvShow = tvShowRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(TvShow.class));

        if (tvShowRequest.getTitle() != null) tvShow.setTitle(tvShowRequest.getTitle());
        if (tvShowRequest.getDescription() != null) tvShow.setDescription(tvShowRequest.getDescription());

        if (tvShowRequest.getThumbnailUrl() != null) {
            verifyThumbnailUrl(tvShowRequest);
            tvShow.setThumbnailUrl(tvShowRequest.getThumbnailUrl());
        }

        if (tvShowRequest.getAnimationUrl() != null) {
            verifyAnimationUrl(tvShowRequest);
            tvShow.setAnimationUrl(tvShowRequest.getAnimationUrl());
        }

        if (tvShowRequest.getCategories() != null) {
            if (!tvShowRequest.getCategories().isEmpty()) tvShow.setCategories(tvShowRequest.getCategories());
        }

        var patchedTvShow = tvShowRepository.save(tvShow);

        return mapper.convertValue(patchedTvShow, TvShowResponse.class);
    }

    public void delete(String id) throws Throwable {

        deleteOrphanSeasons(id);
        deleteOrphanRatings(id);

        var tvShow = findById(id);
        var movieThumbnail = tvShow.getThumbnailUrl();
        var movieAnimation = tvShow.getAnimationUrl();

        cloudStorageService.deleteFile(movieThumbnail);
        cloudStorageService.deleteFile(movieAnimation);

        tvShowRepository.deleteById(id);
    }

    private void deleteOrphanSeasons(String tvShowId) {

        var seasons = seasonService.findByTvShowId(tvShowId);

        seasons.forEach(season -> seasonService.deleteOrphanEpisodes(season.getId()));

        seasonRepository.deleteAll(mapper.convertList(seasons, Season.class));
    }

    private void deleteOrphanRatings(String tvShowId) {

        var tvShow = tvShowRepository.findById(tvShowId).orElseThrow(() -> new EntityNotFoundException(TvShow.class));

        ratingRepository.deleteAll(ratingRepository.findAll().stream().filter(r -> tvShow.getRatings().contains(r)).toList());
    }

    private void verifyFileUrl(TvShowRequest tvShowRequest) {
        verifyThumbnailUrl(tvShowRequest);
        verifyAnimationUrl(tvShowRequest);
    }
}
