package br.com.pds.streaming.media.repositories;

import br.com.pds.streaming.media.model.entities.Episode;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EpisodeRepository extends MongoRepository<Episode, String> {
}
