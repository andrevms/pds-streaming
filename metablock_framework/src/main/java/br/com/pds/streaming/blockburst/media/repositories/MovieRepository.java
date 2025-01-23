package br.com.pds.streaming.blockburst.media.repositories;

import br.com.pds.streaming.blockburst.media.model.entities.Movie;
import br.com.pds.streaming.framework.media.repositories.MediaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieRepository extends MediaRepository<Movie, String> {
}
