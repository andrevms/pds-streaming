package br.com.pds.streaming.media.repositories;

import br.com.pds.streaming.media.model.entities.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieRepository extends MongoRepository<Movie, String> {
}
