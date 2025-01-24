package br.com.pds.streaming.blockfy.media.services;

import br.com.pds.streaming.blockburst.mapper.modelMapper.BlockburstMapper;
import br.com.pds.streaming.blockburst.media.model.entities.Episode;
import br.com.pds.streaming.blockburst.media.model.entities.Movie;
import br.com.pds.streaming.blockburst.media.repositories.EpisodeRepository;
import br.com.pds.streaming.blockburst.media.repositories.MovieRepository;
import br.com.pds.streaming.blockfy.mapper.modelMapper.BlockfyMapper;
import br.com.pds.streaming.blockfy.media.model.entities.Music;
import br.com.pds.streaming.blockfy.media.model.entities.Podcast;
import br.com.pds.streaming.blockfy.media.repositories.MusicRepository;
import br.com.pds.streaming.blockfy.media.repositories.PodcastRepository;
import br.com.pds.streaming.framework.authentication.model.entities.User;
import br.com.pds.streaming.framework.authentication.repositories.UserRepository;
import br.com.pds.streaming.framework.exceptions.EntityNotFoundException;
import br.com.pds.streaming.framework.media.model.dto.HistoryNodeDTO;
import br.com.pds.streaming.framework.media.model.entities.History;
import br.com.pds.streaming.framework.media.model.entities.HistoryNode;
import br.com.pds.streaming.framework.media.repositories.HistoryNodeRepository;
import br.com.pds.streaming.framework.media.repositories.HistoryRepository;
import br.com.pds.streaming.framework.media.services.HistoryNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlockfyHistoryNodeService extends HistoryNodeService {

    private final HistoryRepository historyRepository;
    private final MusicRepository musicRepository;
    private final PodcastRepository podcastRepository;

    @Autowired
    public BlockfyHistoryNodeService(HistoryNodeRepository historyNodeRepository, BlockfyMapper mapper, HistoryRepository historyRepository, MusicRepository musicRepository, PodcastRepository podcastRepository) {
        super(historyNodeRepository, mapper);
        this.historyRepository = historyRepository;
        this.musicRepository = musicRepository;
        this.podcastRepository = podcastRepository;
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

        if (dto.getType().toUpperCase().equals("MUSIC")) {
            var media = musicRepository.findById(mediaId).orElseThrow(() -> new EntityNotFoundException(Music.class));

            var userId = historyRepository.findById(historyId).orElseThrow(() -> new EntityNotFoundException(History.class)).getUserId();

            media.getUsersId().add(userId);

            musicRepository.save(media);
            historyNode.setMedia(media);
        }
        else {
            var media = podcastRepository.findById(mediaId).orElseThrow(() -> new EntityNotFoundException(Podcast.class));
            historyNode.setMedia(media);
        }

        historyNode.setType(dto.getType().toUpperCase());

        return historyNode;
    }
}
