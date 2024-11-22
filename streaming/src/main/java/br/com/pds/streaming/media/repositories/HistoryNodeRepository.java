package br.com.pds.streaming.media.repositories;

import br.com.pds.streaming.media.model.entities.HistoryNode;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HistoryNodeRepository extends MongoRepository<HistoryNode, String> {
}
