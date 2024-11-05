package br.com.pds.streaming.media.services;

import br.com.pds.streaming.exceptions.ObjectNotFoundException;
import br.com.pds.streaming.mapper.modelMapper.MyModelMapper;
import br.com.pds.streaming.media.model.dto.EpisodeDTO;
import br.com.pds.streaming.media.model.entities.Episode;
import br.com.pds.streaming.media.repositories.EpisodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EpisodeService {

    @Autowired
    private EpisodeRepository episodeRepository;

    @Autowired
    private MyModelMapper mapper;

    public List<EpisodeDTO> findAll() {

        var episodes = episodeRepository.findAll();

        return mapper.convertList(episodes, EpisodeDTO.class);
    }

    public List<EpisodeDTO> findBySeasonId(String seasonId) {

        var episodes = episodeRepository.findBySeasonId(seasonId);

        return mapper.convertList(episodes, EpisodeDTO.class);
    }

    public EpisodeDTO findById(String id) throws ObjectNotFoundException {

        var episode = episodeRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Episode not found."));

        return mapper.convertValue(episode, EpisodeDTO.class);
    }

    public EpisodeDTO insert(EpisodeDTO episodeDTO) {

        var createdEpisode = episodeRepository.save(mapper.convertValue(episodeDTO, Episode.class));

        return mapper.convertValue(createdEpisode, EpisodeDTO.class);
    }

    public EpisodeDTO update(EpisodeDTO episodeDTO, String id) throws ObjectNotFoundException {

        var episode = episodeRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Episode not found."));

        episode.setTitle(episodeDTO.getTitle());
        episode.setDescription(episodeDTO.getDescription());
        episode.setVideoUrl(episodeDTO.getVideoUrl());
        episode.setThumbnailUrl(episodeDTO.getThumbnailUrl());
        episode.setAnimationUrl(episodeDTO.getAnimationUrl());

        var updatedEpisode = episodeRepository.save(episode);

        return mapper.convertValue(updatedEpisode, EpisodeDTO.class);
    }

    public void delete(String id) {
        episodeRepository.deleteById(id);
    }
}
