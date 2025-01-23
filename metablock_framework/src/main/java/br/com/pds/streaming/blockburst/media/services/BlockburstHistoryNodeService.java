package br.com.pds.streaming.blockburst.media.services;

import br.com.pds.streaming.blockburst.mapper.modelMapper.BlockburstMapper;
import br.com.pds.streaming.blockburst.media.model.entities.Movie;
import br.com.pds.streaming.blockburst.media.repositories.EpisodeRepository;
import br.com.pds.streaming.blockburst.media.repositories.MovieRepository;
import br.com.pds.streaming.framework.exceptions.EntityNotFoundException;
import br.com.pds.streaming.framework.media.model.dto.HistoryNodeDTO;
import br.com.pds.streaming.framework.media.model.entities.History;
import br.com.pds.streaming.framework.media.model.entities.HistoryNode;
import br.com.pds.streaming.framework.media.model.entities.Media;
import br.com.pds.streaming.framework.media.repositories.HistoryNodeRepository;
import br.com.pds.streaming.framework.media.repositories.HistoryRepository;
import br.com.pds.streaming.framework.media.services.HistoryNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BlockburstHistoryNodeService extends HistoryNodeService {

    private final HistoryRepository historyRepository;
    private final MovieRepository movieRepository;
    private final EpisodeRepository episodeRepository;

    @Autowired
    public BlockburstHistoryNodeService(HistoryNodeRepository historyNodeRepository, BlockburstMapper mapper, HistoryRepository historyRepository, MovieRepository movieRepository, EpisodeRepository episodeRepository) {
        super(historyNodeRepository, mapper);
        this.historyRepository = historyRepository;
        this.movieRepository = movieRepository;
        this.episodeRepository = episodeRepository;
    }

    @Override
    public HistoryNodeDTO insert(String mediaId, HistoryNodeDTO historyNodeDTO, String historyId) {
        
        var history = historyRepository.findById(historyId).orElseThrow(() -> new EntityNotFoundException(History.class));

        var historyNode = dtoToHistoryNode(historyNodeDTO, historyId, mediaId);
        Optional<Movie> movie = movieRepository.findById(mediaId);
        if (movie.isPresent()) {
            historyNode.setMedia(movie.get());
        } else {
            historyNode.setMedia(episodeRepository.findById(mediaId).orElseThrow(() -> new EntityNotFoundException(Media.class)));
        }

        var createdHistoryNode = historyNodeRepository.save(historyNode);

        history.getNodes().add(createdHistoryNode);

        historyRepository.save(history);

        return mapper.convertValue(createdHistoryNode, HistoryNodeDTO.class);
    }

    private HistoryNode dtoToHistoryNode(HistoryNodeDTO dto, String historyId, String mediaId) {

        HistoryNode historyNode = new HistoryNode();

        historyNode.setCurrentTime(dto.getCurrentTime());
        historyNode.setHistoryId(historyId);

        Optional<Movie> movie = movieRepository.findById(mediaId);
        if (movie.isPresent()) {
            historyNode.setMedia(movie.get());
        } else {
            historyNode.setMedia(episodeRepository.findById(mediaId).orElseThrow(() -> new EntityNotFoundException(Media.class)));
        }

        return historyNode;
    }
}