package br.com.pds.streaming.yulearn.media.services;

import br.com.pds.streaming.blockburst.mapper.modelMapper.BlockburstMapper;
import br.com.pds.streaming.blockburst.media.model.entities.Episode;
import br.com.pds.streaming.blockburst.media.model.entities.Movie;
import br.com.pds.streaming.blockburst.media.repositories.EpisodeRepository;
import br.com.pds.streaming.blockburst.media.repositories.MovieRepository;
import br.com.pds.streaming.framework.exceptions.EntityNotFoundException;
import br.com.pds.streaming.framework.media.model.dto.HistoryNodeDTO;
import br.com.pds.streaming.framework.media.model.entities.History;
import br.com.pds.streaming.framework.media.model.entities.HistoryNode;
import br.com.pds.streaming.framework.media.repositories.HistoryNodeRepository;
import br.com.pds.streaming.framework.media.repositories.HistoryRepository;
import br.com.pds.streaming.framework.media.services.HistoryNodeService;
import br.com.pds.streaming.yulearn.mapper.modelMapper.YulearnMapper;
import br.com.pds.streaming.yulearn.media.repositories.VideoLessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YulearnHistoryNodeService extends HistoryNodeService {

    private final HistoryRepository historyRepository;
    private final VideoLessonRepository videoLessonRepository;

    @Autowired
    public YulearnHistoryNodeService(HistoryNodeRepository historyNodeRepository, YulearnMapper mapper, HistoryRepository historyRepository, VideoLessonRepository videoLessonRepository) {
        super(historyNodeRepository, mapper);
        this.historyRepository = historyRepository;
        this.videoLessonRepository = videoLessonRepository;
    }

    @Override
    public HistoryNodeDTO insert(String mediaId, HistoryNodeDTO historyNodeDTO, String historyId) {
        
        var history = historyRepository.findById(historyId).orElseThrow(() -> new EntityNotFoundException(History.class));

        var historyNode = dtoToHistoryNode(historyNodeDTO, historyId, mediaId);

        var createdHistoryNode = historyNodeRepository.save(historyNode);

        history.getNodes().add(createdHistoryNode);

        historyRepository.save(history);

        return mapper.convertValue(createdHistoryNode, HistoryNodeDTO.class);
    }

    private HistoryNode dtoToHistoryNode(HistoryNodeDTO dto, String historyId, String mediaId) {

        HistoryNode historyNode = new HistoryNode();

        historyNode.setCurrentTime(dto.getCurrentTime());
        historyNode.setHistoryId(historyId);

        if (dto.getType().toUpperCase().equals("VIDEO-LESSON")) {
            var media = videoLessonRepository.findById(mediaId).orElseThrow(() -> new EntityNotFoundException(VideoLessonService.class));
            historyNode.setMedia(media);
        }

        historyNode.setType(dto.getType().toUpperCase());

        return historyNode;
    }
}