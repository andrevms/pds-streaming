package br.com.pds.streaming.blockburst.media.repositories;

import br.com.pds.streaming.blockburst.media.model.entities.Season;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SeasonRepository extends MongoRepository<Season, String> {
    List<Season> findByTvShowId(String tvShowId);
}
