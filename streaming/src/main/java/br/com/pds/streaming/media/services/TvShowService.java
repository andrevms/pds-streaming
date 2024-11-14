package br.com.pds.streaming.media.services;

import br.com.pds.streaming.exceptions.ObjectNotFoundException;
import br.com.pds.streaming.mapper.modelMapper.MyModelMapper;
import br.com.pds.streaming.media.model.dto.TvShowDTO;
import br.com.pds.streaming.media.model.entities.Rating;
import br.com.pds.streaming.media.model.entities.TvShow;
import br.com.pds.streaming.media.repositories.EpisodeRepository;
import br.com.pds.streaming.media.repositories.RatingRepository;
import br.com.pds.streaming.media.repositories.SeasonRepository;
import br.com.pds.streaming.media.repositories.TvShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TvShowService {

    private TvShowRepository tvShowRepository;
    private SeasonRepository seasonRepository;
    private EpisodeRepository episodeRepository;
    private RatingRepository ratingRepository;
    private MyModelMapper mapper;

    @Autowired
    public TvShowService(TvShowRepository tvShowRepository, SeasonRepository seasonRepository, EpisodeRepository episodeRepository, RatingRepository ratingRepository, MyModelMapper mapper) {
        this.tvShowRepository = tvShowRepository;
        this.seasonRepository = seasonRepository;
        this.episodeRepository = episodeRepository;
        this.ratingRepository = ratingRepository;
        this.mapper = mapper;
    }

    public List<TvShowDTO> findAll() {

        var tvShows = tvShowRepository.findAll();

        var tvShowsDTO = mapper.convertList(tvShows, TvShowDTO.class);

        tvShowsDTO.forEach(x -> {
            var tvShowRatings = ratingRepository.findAll().stream().filter(r -> r.getTvShowId() != null).filter(r -> r.getTvShowId().equals(x.getId())).toList();
            x.setRatingsAverage(!tvShowRatings.isEmpty() ? String.format("%.1f", tvShowRatings.stream().mapToDouble(Rating::getStars).sum() / tvShowRatings.size()) : "Não há avaliações para esta série.");
        });

        return tvShowsDTO;
    }

    public TvShowDTO findById(String id) throws ObjectNotFoundException {

        var tvShow = tvShowRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(TvShow.class));

        var tvShowDTO = mapper.convertValue(tvShow, TvShowDTO.class);

        var tvShowRatings = ratingRepository.findAll().stream().filter(r -> r.getTvShowId() != null).filter(r -> r.getTvShowId().equals(id)).toList();

        tvShowDTO.setRatingsAverage(!tvShowRatings.isEmpty() ? String.format("%.1f", tvShowRatings.stream().mapToDouble(Rating::getStars).sum() / tvShowRatings.size()) : "Não há avaliações para esta série.");

        return tvShowDTO;
    }

    public TvShowDTO insert(TvShowDTO tvShowDTO) {

        var createdTvShow = tvShowRepository.save(mapper.convertValue(tvShowDTO, TvShow.class));

        return mapper.convertValue(createdTvShow, TvShowDTO.class);
    }

    public TvShowDTO update(TvShowDTO tvShowDTO, String id) throws ObjectNotFoundException {

        var tvShow = tvShowRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(TvShow.class));

        tvShow.setTitle(tvShowDTO.getTitle());
        tvShow.setDescription(tvShowDTO.getDescription());
        tvShow.setThumbnailUrl(tvShowDTO.getThumbnailUrl());
        tvShow.setAnimationUrl(tvShowDTO.getAnimationUrl());

        var updatedTvShow = tvShowRepository.save(tvShow);

        return mapper.convertValue(updatedTvShow, TvShowDTO.class);
    }

    public void delete(String id) {

        deleteOrphanSeasons(id);
        deleteOrphanRatings(id);

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

    private void deleteOrphanRatings(String tvShowId) {

        var ratings = ratingRepository.findByTvShowId(tvShowId);

        ratingRepository.deleteAll(ratings);
    }
}
