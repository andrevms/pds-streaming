package br.com.pds.streaming.blockburst.repositories;

import br.com.pds.streaming.blockburst.media.model.entities.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieRepository extends MongoRepository<Movie, String> {
}
