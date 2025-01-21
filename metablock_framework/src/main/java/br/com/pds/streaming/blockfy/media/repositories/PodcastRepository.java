package br.com.pds.streaming.blockfy.media.repositories;

import br.com.pds.streaming.blockfy.media.model.entities.Music;
import br.com.pds.streaming.blockfy.media.model.entities.Podcast;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PodcastRepository extends MongoRepository<Podcast, String> {
}
