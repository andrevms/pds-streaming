package br.com.pds.streaming.blockburst.media.services;

import br.com.pds.streaming.blockburst.mapper.modelMapper.BlockburstMapper;
import br.com.pds.streaming.blockburst.media.model.dto.TvShowRequest;
import br.com.pds.streaming.blockburst.media.model.dto.TvShowResponse;
import br.com.pds.streaming.blockburst.media.model.entities.Season;
import br.com.pds.streaming.blockburst.media.model.entities.TvShow;
import br.com.pds.streaming.blockburst.repositories.SeasonRepository;
import br.com.pds.streaming.blockburst.repositories.TvShowRepository;
import br.com.pds.streaming.framework.cloud.services.CloudStorageService;
import br.com.pds.streaming.framework.exceptions.EntityNotFoundException;
import br.com.pds.streaming.framework.exceptions.InvalidAnimationException;
import br.com.pds.streaming.framework.exceptions.InvalidThumbnailException;
import br.com.pds.streaming.framework.media.repositories.LikeRatingRepository;
import br.com.pds.streaming.framework.media.repositories.MediaRepository;
import br.com.pds.streaming.framework.media.util.FileExtensionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TvShowService {

    private final TvShowRepository tvShowMediaRepository;
    private final SeasonService seasonService;
    private final LikeRatingRepository ratingRepository;
    private final BlockburstMapper mapper;
    private final CloudStorageService cloudStorageService;
    private final SeasonRepository seasonRepository;

    @Autowired // Tentar instanciar o MediaService com o BlockburstMapper dinamicamente
    public TvShowService(TvShowRepository tvShowMediaRepository, SeasonService seasonService, LikeRatingRepository ratingRepository, BlockburstMapper mapper, CloudStorageService cloudStorageService, SeasonRepository seasonRepository) {
        this.tvShowMediaRepository = tvShowMediaRepository;
        this.seasonService = seasonService;
        this.ratingRepository = ratingRepository;
        this.mapper = mapper;
        this.cloudStorageService = cloudStorageService;
        this.seasonRepository = seasonRepository;
    }

    public List<TvShowResponse> findAll() {
        return mapper.convertList(tvShowMediaRepository.findAll(), TvShowResponse.class);
    }

    public TvShowResponse findById(String id) throws Throwable {
        return mapper.convertValue((TvShow) tvShowMediaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(TvShow.class)), TvShowResponse.class);
    }

    public TvShowResponse insert(TvShowRequest tvShowRequest) {

        verifyFilesUrl(tvShowRequest);

        var tvShow = mapper.convertValue(tvShowRequest, TvShow.class);

        var entity = tvShowMediaRepository.save(tvShow);

        return mapper.convertValue(entity, TvShowResponse.class);
    }

    public TvShowResponse update(TvShowRequest tvShowRequest, String id) throws Throwable {

        verifyFilesUrl(tvShowRequest);

        TvShow tvShow = (TvShow) tvShowMediaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(TvShow.class));

        tvShow.setTitle(tvShowRequest.getTitle());
        tvShow.setDescription(tvShowRequest.getDescription());
        tvShow.setThumbnailUrl(tvShowRequest.getThumbnailUrl());
        tvShow.setAnimationUrl(tvShowRequest.getAnimationUrl());
        tvShow.setCategories(tvShow.getCategories());

        var updatedTvShow = tvShowMediaRepository.save(tvShow);

        return mapper.convertValue(updatedTvShow, TvShowResponse.class);
    }

    public TvShowResponse patch(TvShowRequest tvShowRequest, String id) throws Throwable {

        var tvShow = (TvShow) tvShowMediaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(TvShow.class));

        if (tvShowRequest.getTitle() != null) tvShow.setTitle(tvShowRequest.getTitle());
        if (tvShowRequest.getDescription() != null) tvShow.setDescription(tvShowRequest.getDescription());

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

        var patchedTvShow = tvShowMediaRepository.save(tvShow);

        return mapper.convertValue(patchedTvShow, TvShowResponse.class);
    }

    public void delete(String id) throws Throwable {

        deleteOrphanSeasons(id);
        deleteOrphanRatings(id);

        var tvShow = findById(id);
        var movieThumb = tvShow.getThumbnailUrl();
        var movieAnimation = tvShow.getAnimationUrl();

        cloudStorageService.deleteFile(movieThumb);
        cloudStorageService.deleteFile(movieAnimation);

        tvShowMediaRepository.deleteById(id);
    }

    protected void deleteOrphanSeasons(String tvShowId) {

        var seasons = seasonService.findByTvShowId(tvShowId);

        seasons.forEach(season -> seasonService.deleteOrphanEpisodes(season.getId()));

        seasonRepository.deleteAll(mapper.convertList(seasons, Season.class));
    }

    protected void deleteOrphanRatings(String tvShowId) throws Throwable {

        var tvShow = (TvShow) tvShowMediaRepository.findById(tvShowId).orElseThrow(() -> new EntityNotFoundException(TvShow.class));

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
