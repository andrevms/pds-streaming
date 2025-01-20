package br.com.pds.streaming.framework.media.repositories;

import br.com.pds.streaming.framework.media.model.entities.StarRating;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StarRatingRepository extends MongoRepository<StarRating, String> {
    List<StarRating> findByUserId(String userId);
}
