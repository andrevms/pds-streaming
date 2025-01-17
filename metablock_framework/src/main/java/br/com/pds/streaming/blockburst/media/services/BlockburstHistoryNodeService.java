package br.com.pds.streaming.blockburst.media.services;

import br.com.pds.streaming.blockburst.mapper.modelMapper.BlockburstMapper;
import br.com.pds.streaming.blockburst.media.model.entities.Episode;
import br.com.pds.streaming.blockburst.media.model.entities.Movie;
import br.com.pds.streaming.framework.exceptions.EntityNotFoundException;
import br.com.pds.streaming.framework.media.model.dto.HistoryNodeDTO;
import br.com.pds.streaming.framework.media.model.entities.History;
import br.com.pds.streaming.framework.media.model.entities.HistoryNode;
import br.com.pds.streaming.framework.media.model.entities.Media;
import br.com.pds.streaming.framework.media.repositories.HistoryNodeRepository;
import br.com.pds.streaming.framework.media.repositories.HistoryRepository;
import br.com.pds.streaming.framework.media.repositories.MediaRepository;
import br.com.pds.streaming.framework.media.services.HistoryNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlockburstHistoryNodeService extends HistoryNodeService {

    private final HistoryRepository historyRepository;
    private final MediaRepository mediaRepository;

    @Autowired
    public BlockburstHistoryNodeService(HistoryNodeRepository historyNodeRepository, BlockburstMapper mapper, HistoryRepository historyRepository, MediaRepository mediaRepository) {
        super(historyNodeRepository, mapper);
        this.historyRepository = historyRepository;
        this.mediaRepository = mediaRepository;
    }

    @Override
    public HistoryNodeDTO insert(String mediaId, HistoryNodeDTO historyNodeDTO, String historyId) {
        
        var history = historyRepository.findById(historyId).orElseThrow(() -> new EntityNotFoundException(History.class));

        var historyNode = dtoToHistoryNode(historyNodeDTO, historyId, mediaId);
        historyNode.setMedia(mediaRepository.findById(mediaId).orElseThrow(() -> new EntityNotFoundException(Media.class)));

        var createdHistoryNode = historyNodeRepository.save(historyNode);

        history.getNodes().add(createdHistoryNode);

        historyRepository.save(history);

        return mapper.convertValue(createdHistoryNode, HistoryNodeDTO.class);
    }

    private HistoryNode dtoToHistoryNode(HistoryNodeDTO dto, String historyId, String mediaId) {

        HistoryNode historyNode = new HistoryNode();

        historyNode.setCurrentTime(dto.getCurrentTime());
        historyNode.setHistoryId(historyId);

        if (mediaRepository.findById(mediaId).orElseThrow(() -> new EntityNotFoundException(Media.class)) instanceof Movie) {
            var media = (Movie) mediaRepository.findById(mediaId).orElseThrow(() -> new EntityNotFoundException(Movie.class));
            historyNode.setMedia(media);
        }
        else {
            var media = (Episode) mediaRepository.findById(mediaId).orElseThrow(() -> new EntityNotFoundException(Episode.class));
            historyNode.setMedia(media);
        }

        return historyNode;
    }
}