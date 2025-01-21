package br.com.pds.streaming.blockburst.media.repositories;

import br.com.pds.streaming.blockburst.media.model.entities.Season;
import br.com.pds.streaming.blockburst.media.model.entities.TvShow;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TvShowRepository extends MongoRepository<TvShow, String> {
}