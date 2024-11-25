package br.com.pds.streaming.media.repositories;

import br.com.pds.streaming.media.model.entities.HistoryNode;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface HistoryNodeRepository extends MongoRepository<HistoryNode, String> {
    List<HistoryNode> findByHistoryId(String historyId);
}
