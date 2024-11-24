package br.com.pds.streaming.media.repositories;

import br.com.pds.streaming.media.model.entities.Movie;
import br.com.pds.streaming.media.model.entities.TvShow;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface TvShowRepository extends MongoRepository<TvShow, String> {

    @Query("{ 'title': { $regex: ?0, $options: 'i' } }")
    List<TvShow> findByTvShowTitleContainingIgnoreCase(String title);
}
