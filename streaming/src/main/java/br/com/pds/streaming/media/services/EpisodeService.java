package br.com.pds.streaming.media.services;

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

    public Episode findById(String id) {
        Optional<Episode> episode = episodeRepository.findById(id);
        return episode.orElse(null); // throw an exception later
    }

    public List<Episode> findAll() {
        return episodeRepository.findAll();
    }

    public List<Episode> findBySeason(Season season) {
        return episodeRepository.findBySeason(season);
    }

    public Episode insert(Episode episode) {
        return episodeRepository.save(episode);
    }

    public Episode update(String id, Episode episode) {
        if (!episodeRepository.existsById(id)) {
            return null; // throw an exception later
        }

        episode.setId(new ObjectId(id));
        return episodeRepository.save(episode);
    }

    public void delete(String id) {
        episodeRepository.deleteById(id);
    }
}
