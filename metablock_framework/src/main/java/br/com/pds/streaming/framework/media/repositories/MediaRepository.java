package br.com.pds.streaming.framework.media.repositories;

import br.com.pds.streaming.framework.media.model.entities.Media;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MediaRepository<M extends Media, ID> extends MongoRepository<M, ID> {
}
