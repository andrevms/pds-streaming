package br.com.pds.streaming.media.repositories;

import br.com.pds.streaming.media.model.entities.TvShow;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TvShowRepository extends MongoRepository<TvShow, String> {
}
