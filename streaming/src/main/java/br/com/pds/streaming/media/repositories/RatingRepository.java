package br.com.pds.streaming.media.repositories;

import br.com.pds.streaming.media.model.entities.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RatingRepository extends MongoRepository<Rating, String> {
    List<Rating> findByBusinessUserId(String userId);
    List<Rating> findByMovieId(String movieId);
    List<Rating> findByTvShowId(String tvShowId);
}
