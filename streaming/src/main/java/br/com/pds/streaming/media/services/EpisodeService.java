package br.com.pds.streaming.media.services;

import br.com.pds.streaming.exceptions.ObjectNotFoundException;
import br.com.pds.streaming.media.model.entities.Episode;
import br.com.pds.streaming.media.model.entities.Season;
import br.com.pds.streaming.media.repositories.EpisodeRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EpisodeService {

    @Autowired
    private EpisodeRepository episodeRepository;

    public Episode findById(String id) throws ObjectNotFoundException {
        Optional<Episode> episode = episodeRepository.findById(id);
        return episode.orElseThrow(() -> new ObjectNotFoundException("Episode not found."));
    }

    public List<Episode> findAll() {
        return episodeRepository.findAll();
    }

    public List<Episode> findBySeasonId(String seasonId) {
        return episodeRepository.findBySeasonId(seasonId);
    }

    public Episode insert(Episode episode) {
        return episodeRepository.save(episode);
    }

    public Episode update(String id, Episode episode) throws ObjectNotFoundException {
        if (!episodeRepository.existsById(id)) {
            throw new ObjectNotFoundException("Episode not found.");
        }

        episode.setId(new ObjectId(id));
        return episodeRepository.save(episode);
    }

    public void delete(String id) {
        episodeRepository.deleteById(id);
    }
}
