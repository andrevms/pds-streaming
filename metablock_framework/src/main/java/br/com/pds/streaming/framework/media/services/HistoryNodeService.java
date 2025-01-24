package br.com.pds.streaming.framework.media.services;

import br.com.pds.streaming.blockburst.mapper.modelMapper.BlockburstMapper;
import br.com.pds.streaming.framework.exceptions.EntityNotFoundException;
import br.com.pds.streaming.framework.mapper.modelMapper.MetablockMapper;
import br.com.pds.streaming.framework.media.model.dto.HistoryNodeDTO;
import br.com.pds.streaming.framework.media.model.dto.request.HistoryNodeRequest;
import br.com.pds.streaming.framework.media.model.entities.HistoryNode;
import br.com.pds.streaming.framework.media.repositories.HistoryNodeRepository;
import org.modelmapper.ModelMapper;

import java.util.List;

public abstract class HistoryNodeService {

    protected final HistoryNodeRepository historyNodeRepository;
    protected final MetablockMapper mapper;

    public HistoryNodeService(HistoryNodeRepository historyNodeRepository, MetablockMapper mapper) {
        this.historyNodeRepository = historyNodeRepository;
        this.mapper = mapper;
    }

    public List<HistoryNode> findAll() {

        var historyNodes = historyNodeRepository.findAll()/*.stream().map(node -> mapper.convertValue(node, HistoryNodeDTO.class)).toList()*/;

        return historyNodes;
    }

    public List<HistoryNodeDTO> findByHistoryId(String historyId) {
        return historyNodeRepository.findByHistoryId(historyId).stream().map(node -> mapper.convertValue(node, HistoryNodeDTO.class)).toList();
    }

    public HistoryNode findById(String id) {

        var historyNode = historyNodeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(HistoryNode.class));

        return historyNode;
    }

    public abstract HistoryNodeDTO insert(String mediaId, HistoryNodeRequest historyNodeRequest, String historyId);

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
