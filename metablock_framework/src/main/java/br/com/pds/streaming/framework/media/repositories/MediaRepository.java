package br.com.pds.streaming.framework.media.repositories;

import br.com.pds.streaming.framework.media.model.entities.Media;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MediaRepository extends MongoRepository<Media, String> {
    List<Media> findByTitle(String title);
}
