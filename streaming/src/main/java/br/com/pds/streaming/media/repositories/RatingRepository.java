package br.com.pds.streaming.media.repositories;

import br.com.pds.streaming.media.model.entities.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RatingRepository extends MongoRepository<Rating, String> {
    Rating findByBusinessUserId(String userId);
}
