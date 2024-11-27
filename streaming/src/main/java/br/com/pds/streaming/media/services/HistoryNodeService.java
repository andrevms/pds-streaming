package br.com.pds.streaming.media.services;

import br.com.pds.streaming.exceptions.EntityNotFoundException;
import br.com.pds.streaming.mapper.modelMapper.MyModelMapper;
import br.com.pds.streaming.media.model.dto.HistoryNodeDTO;
import br.com.pds.streaming.media.model.entities.Episode;
import br.com.pds.streaming.media.model.entities.History;
import br.com.pds.streaming.media.model.entities.HistoryNode;
import br.com.pds.streaming.media.model.entities.Movie;
import br.com.pds.streaming.media.repositories.EpisodeRepository;
import br.com.pds.streaming.media.repositories.HistoryNodeRepository;
import br.com.pds.streaming.media.repositories.HistoryRepository;
import br.com.pds.streaming.media.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class HistoryNodeService {

    private final HistoryNodeRepository historyNodeRepository;
    private final HistoryRepository historyRepository;
    private final EpisodeRepository episodeRepository;
    private final MovieRepository movieRepository;
    private final MyModelMapper mapper;

    @Autowired
    public HistoryNodeService(HistoryNodeRepository historyNodeRepository, HistoryRepository historyRepository, EpisodeRepository episodeRepository, MovieRepository movieRepository, MyModelMapper mapper) {
        this.historyNodeRepository = historyNodeRepository;
        this.historyRepository = historyRepository;
        this.episodeRepository = episodeRepository;
        this.movieRepository = movieRepository;
        this.mapper = mapper;
    }

    public List<HistoryNodeDTO> findAll() {
        return historyNodeRepository.findAll().stream().map(node -> mapper.convertValue(node, HistoryNodeDTO.class))/*.sorted(Collections.reverseOrder())*/.toList();
    }


    public List<HistoryNodeDTO> findByHistoryId(String historyId) {
        return historyNodeRepository.findByHistoryId(historyId).stream().map(node -> mapper.convertValue(node, HistoryNodeDTO.class))/*.sorted(Collections.reverseOrder())*/.toList();
    }

    public HistoryNodeDTO findById(String id) throws EntityNotFoundException {

        var historyNode = historyNodeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(HistoryNode.class));

        return mapper.convertValue(historyNode, HistoryNodeDTO.class);
    }

    public HistoryNodeDTO insert(String episodeId, HistoryNodeDTO historyNodeDTO, String historyId) throws EntityNotFoundException {

        var episode = episodeRepository.findById(episodeId).orElseThrow(() -> new EntityNotFoundException(Episode.class));

        var history = historyRepository.findById(historyId).orElseThrow(() -> new EntityNotFoundException(History.class));

        var historyNode = mapper.convertValue(historyNodeDTO, HistoryNode.class);
        historyNode.setMedia(episode);

        var createdHistoryNode = historyNodeRepository.save(historyNode);

        history.getNodes().add(createdHistoryNode);

        historyRepository.save(history);

        return mapper.convertValue(createdHistoryNode, HistoryNodeDTO.class);
    }

    public HistoryNodeDTO insert(HistoryNodeDTO historyNodeDTO, String movieId, String historyId) throws EntityNotFoundException {

        var movie = movieRepository.findById(movieId).orElseThrow(() -> new EntityNotFoundException(Movie.class));

        var history = historyRepository.findById(historyId).orElseThrow(() -> new EntityNotFoundException(History.class));

        var historyNode = mapper.convertValue(historyNodeDTO, HistoryNode.class);
        historyNode.setMedia(movie);

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
