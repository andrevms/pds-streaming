package br.com.pds.streaming.media.repositories;

import br.com.pds.streaming.media.model.entities.History;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface HistoryRepository extends MongoRepository<History, String> {
    List<History> findByUserId(String userId);
}
