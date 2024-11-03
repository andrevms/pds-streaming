package br.com.pds.streaming.media.repositories;

import br.com.pds.streaming.media.model.entities.Episode;
import br.com.pds.streaming.media.model.entities.Season;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EpisodeRepository extends MongoRepository<Episode, String> {
    List<Episode> findBySeason(Season season);
}
