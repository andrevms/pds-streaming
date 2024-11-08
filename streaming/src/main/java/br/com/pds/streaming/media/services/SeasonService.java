package br.com.pds.streaming.media.services;

import br.com.pds.streaming.exceptions.ObjectNotFoundException;
import br.com.pds.streaming.mapper.modelMapper.MyModelMapper;
import br.com.pds.streaming.media.model.dto.SeasonDTO;
import br.com.pds.streaming.media.model.entities.Season;
import br.com.pds.streaming.media.repositories.SeasonRepository;
import br.com.pds.streaming.media.repositories.TvShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeasonService {

    private SeasonRepository seasonRepository;
    private TvShowRepository tvShowRepository;
    private MyModelMapper mapper;

    @Autowired
    public SeasonService(SeasonRepository seasonRepository, TvShowRepository tvShowRepository, MyModelMapper mapper) {
        this.seasonRepository = seasonRepository;
        this.tvShowRepository = tvShowRepository;
        this.mapper = mapper;
    }

    public List<SeasonDTO> findAll() {

        var seasons = seasonRepository.findAll();

        return mapper.convertList(seasons, SeasonDTO.class);
    }

    public List<SeasonDTO> findByTvShowId(String tvShowId) {

        var seasons = seasonRepository.findByTvShowId(tvShowId);

        return mapper.convertList(seasons, SeasonDTO.class);
    }

    public SeasonDTO findById(String id) throws ObjectNotFoundException {

        var season = seasonRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Season not found."));

        return mapper.convertValue(season, SeasonDTO.class);
    }

    public SeasonDTO insert(SeasonDTO seasonDTO, String tvShowId) throws ObjectNotFoundException {

        var tvShow = tvShowRepository.findById(tvShowId).orElseThrow(() -> new ObjectNotFoundException("TvShow not found."));

        var createdSeason = seasonRepository.save(mapper.convertValue(seasonDTO, Season.class));

        tvShow.getSeasons().add(createdSeason);

        tvShowRepository.save(tvShow);

        return mapper.convertValue(createdSeason, SeasonDTO.class);
    }

    public SeasonDTO update(SeasonDTO seasonDTO, String id) throws ObjectNotFoundException {

        var season = seasonRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Season not found."));

        season.setTitle(seasonDTO.getTitle());
        season.setDescription(seasonDTO.getDescription());
        season.setThumbnailUrl(seasonDTO.getThumbnailUrl());
        season.setAnimationUrl(seasonDTO.getAnimationUrl());

        var updatedSeason = seasonRepository.save(season);

        return mapper.convertValue(updatedSeason, SeasonDTO.class);
    }

    public void delete(String id) {
        seasonRepository.deleteById(id);
    }
}
