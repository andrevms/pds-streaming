package br.com.pds.streaming.media.repositories;

import br.com.pds.streaming.media.model.entities.History;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HistoryRepository extends MongoRepository<History, String> {
}
