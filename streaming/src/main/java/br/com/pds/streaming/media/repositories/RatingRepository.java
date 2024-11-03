package br.com.pds.streaming.media.repositories;

import br.com.pds.streaming.domain.registration.model.entities.User;
import br.com.pds.streaming.media.model.entities.Rating;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface RatingRepository extends MongoRepository<Rating, String> {
    Rating findByUser(User user);

    @Query("{ 'user' : ?0 }")
    Rating findByUserId(ObjectId userId);
}
