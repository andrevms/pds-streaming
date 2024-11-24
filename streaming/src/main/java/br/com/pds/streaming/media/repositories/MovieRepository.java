package br.com.pds.streaming.media.repositories;

import br.com.pds.streaming.media.model.entities.Movie;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends MongoRepository<Movie, String> {

    List<Movie> findMovieByTitle(String title);

    @Query("{ 'title': { $regex: ?0, $options: 'i' } }")
    List<Movie> findMovieByTitleContainingIgnoreCase(String title);
}
