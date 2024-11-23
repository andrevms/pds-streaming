package br.com.pds.streaming.media.services;

import br.com.pds.streaming.exceptions.ObjectNotFoundException;
import br.com.pds.streaming.mapper.modelMapper.MyModelMapper;
import br.com.pds.streaming.media.model.dto.HistoryNodeDTO;
import br.com.pds.streaming.media.model.entities.History;
import br.com.pds.streaming.media.model.entities.HistoryNode;
import br.com.pds.streaming.media.repositories.HistoryNodeRepository;
import br.com.pds.streaming.media.repositories.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryNodeService {

    private final HistoryNodeRepository historyNodeRepository;
    private final HistoryRepository historyRepository;
    private final MyModelMapper mapper;

    @Autowired
    public HistoryNodeService(HistoryNodeRepository historyNodeRepository, HistoryRepository historyRepository, MyModelMapper mapper) {
        this.historyNodeRepository = historyNodeRepository;
        this.historyRepository = historyRepository;
        this.mapper = mapper;
    }

    public List<HistoryNodeDTO> findAll() {

        var historyNodes = historyNodeRepository.findAll();

        return mapper.convertList(historyNodes, HistoryNodeDTO.class);
    }

    public List<HistoryNodeDTO> findByHistoryId(String historyId) {

        var historyNodes = historyNodeRepository.findByHistoryId(historyId);

        return mapper.convertList(historyNodes, HistoryNodeDTO.class);
    }

    public HistoryNodeDTO findById(String id) throws ObjectNotFoundException {

        var historyNode = historyNodeRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(HistoryNode.class));

        return mapper.convertValue(historyNode, HistoryNodeDTO.class);
    }

    public HistoryNodeDTO insert(HistoryNodeDTO historyNodeDTO, String historyId) throws ObjectNotFoundException {

        var history = historyRepository.findById(historyId).orElseThrow(() -> new ObjectNotFoundException(History.class));

        var historyNode = mapper.convertValue(historyNodeDTO, HistoryNode.class);
        historyNode.setHistoryId(historyId);

        var createdHistoryNode = historyNodeRepository.save(historyNode);

        history.getNodes().add(createdHistoryNode);

        historyRepository.save(history);

        return mapper.convertValue(createdHistoryNode, HistoryNodeDTO.class);
    }

    public HistoryNodeDTO update(HistoryNodeDTO historyNodeDTO, String id) {
        return deleteAndInsertAgain(historyNodeDTO, id);
    }

    public HistoryNodeDTO patch(HistoryNodeDTO historyNodeDTO, String id) {
        return deleteAndInsertAgain(historyNodeDTO, id);
    }

    public void delete(String id) {
        historyNodeRepository.deleteById(id);
    }

    private HistoryNodeDTO deleteAndInsertAgain(HistoryNodeDTO historyNodeDTO, String id) {

        var historyNode = mapper.convertValue(historyNodeDTO, HistoryNode.class);

        historyNode.setCurrentTime(historyNodeDTO.getCurrentTime());
        delete(id);
        var updatedHistoryNode = historyNodeRepository.save(historyNode);

        return mapper.convertValue(updatedHistoryNode, HistoryNodeDTO.class);
    }
}