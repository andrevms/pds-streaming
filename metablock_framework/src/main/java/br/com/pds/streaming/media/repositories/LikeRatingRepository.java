package br.com.pds.streaming.media.repositories;

import br.com.pds.streaming.media.model.entities.LikeRating;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LikeRatingRepository extends MongoRepository<LikeRating, String> {
    List<LikeRating> findByUserId(String userId);
}
