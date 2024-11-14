package br.com.pds.streaming.media.services;

import br.com.pds.streaming.exceptions.ObjectNotFoundException;
import br.com.pds.streaming.mapper.modelMapper.MyModelMapper;
import br.com.pds.streaming.media.model.dto.EpisodeDTO;
import br.com.pds.streaming.media.model.entities.Episode;
import br.com.pds.streaming.media.model.entities.Season;
import br.com.pds.streaming.media.repositories.EpisodeRepository;
import br.com.pds.streaming.media.repositories.SeasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EpisodeService {

    private EpisodeRepository episodeRepository;
    private SeasonRepository seasonRepository;
    private MyModelMapper mapper;

    @Autowired
    public EpisodeService(EpisodeRepository episodeRepository, SeasonRepository seasonRepository, MyModelMapper mapper) {
        this.episodeRepository = episodeRepository;
        this.seasonRepository = seasonRepository;
        this.mapper = mapper;
    }

    public List<EpisodeDTO> findAll() {

        var episodes = episodeRepository.findAll();

        return mapper.convertList(episodes, EpisodeDTO.class);
    }

    public List<EpisodeDTO> findBySeasonId(String seasonId) {

        var episodes = episodeRepository.findBySeasonId(seasonId);

        return mapper.convertList(episodes, EpisodeDTO.class);
    }

    public EpisodeDTO findById(String id) throws ObjectNotFoundException {

        var episode = episodeRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(Episode.class));

        return mapper.convertValue(episode, EpisodeDTO.class);
    }

    public EpisodeDTO insert(EpisodeDTO episodeDTO, String seasonId) throws ObjectNotFoundException {

        var season = seasonRepository.findById(seasonId).orElseThrow(() -> new ObjectNotFoundException(Season.class));

        Episode episode = mapper.convertValue(episodeDTO, Episode.class);
        episode.setSeasonId(seasonId);

        var createdEpisode = episodeRepository.save(episode);

        season.getEpisodes().add(createdEpisode);

        seasonRepository.save(season);

        return mapper.convertValue(createdEpisode, EpisodeDTO.class);
    }

    public EpisodeDTO update(EpisodeDTO episodeDTO, String id) throws ObjectNotFoundException {

        var episode = episodeRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(Episode.class));

        episode.setTitle(episodeDTO.getTitle());
        episode.setDescription(episodeDTO.getDescription());
        episode.setVideoUrl(episodeDTO.getVideoUrl());
        episode.setThumbnailUrl(episodeDTO.getThumbnailUrl());
        episode.setAnimationUrl(episodeDTO.getAnimationUrl());

        var updatedEpisode = episodeRepository.save(episode);

        return mapper.convertValue(updatedEpisode, EpisodeDTO.class);
    }

    public EpisodeDTO patch(EpisodeDTO episodeDTO, String id) throws ObjectNotFoundException {

        var episode = episodeRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(Episode.class));

        if (episodeDTO.getTitle() != null) {
            episode.setTitle(episodeDTO.getTitle());
        }

        if (episodeDTO.getDescription() != null) {
            episode.setDescription(episodeDTO.getDescription());
        }

        if (episodeDTO.getVideoUrl() != null) {
            episode.setVideoUrl(episodeDTO.getVideoUrl());
        }

        if (episodeDTO.getThumbnailUrl() != null) {
            episode.setThumbnailUrl(episodeDTO.getThumbnailUrl());
        }

        if (episodeDTO.getAnimationUrl() != null) {
            episode.setAnimationUrl(episodeDTO.getAnimationUrl());
        }

        var patchedEpisode = episodeRepository.save(episode);

        return mapper.convertValue(patchedEpisode, EpisodeDTO.class);
    }

    public void delete(String id) {
        episodeRepository.deleteById(id);
    }
}
