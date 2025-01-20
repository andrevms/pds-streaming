package br.com.pds.streaming.blockfy.repositories;

import br.com.pds.streaming.blockfy.media.model.entities.Music;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MusicRepository extends MongoRepository<Music, String> {
}
