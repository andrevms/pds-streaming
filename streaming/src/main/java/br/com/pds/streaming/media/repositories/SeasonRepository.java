package br.com.pds.streaming.media.repositories;

import br.com.pds.streaming.media.model.entities.Season;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SeasonRepository extends MongoRepository<Season, String> {
}
