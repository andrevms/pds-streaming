package br.com.pds.streaming.media.services;

import br.com.pds.streaming.exceptions.EntityNotFoundException;
import br.com.pds.streaming.mapper.modelMapper.MyModelMapper;
import br.com.pds.streaming.media.model.dto.HistoryNodeDTO;
import br.com.pds.streaming.media.model.dto.HistoryNodeWithEpisodeDTO;
import br.com.pds.streaming.media.model.dto.HistoryNodeWithMovieDTO;
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

        var historyNodes = historyNodeRepository.findAll();

        return historyNodes.stream()
                .map(node -> mapper.convertValue(node, HistoryNodeDTO.class))
                .toList();
    }


    public List<HistoryNodeDTO> findByHistoryId(String historyId) {

        var historyNodes = historyNodeRepository.findByHistoryId(historyId);

        return historyNodes.stream()
                .map(node -> mapper.convertValue(node, HistoryNodeDTO.class))
                .toList();
    }

    public HistoryNodeDTO findById(String id) throws EntityNotFoundException {

        var historyNode = historyNodeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(HistoryNode.class));

        if (historyNode.getEpisode() != null) {
            return mapper.convertValue(historyNode, HistoryNodeWithEpisodeDTO.class);
        }

        if (historyNode.getMovie() != null) {
            return mapper.convertValue(historyNode, HistoryNodeWithMovieDTO.class);
        }

        throw new RuntimeException("History node without episode or movie found.");
    }

    public HistoryNodeDTO insert(String episodeId, HistoryNodeDTO historyNodeDTO, String historyId) throws EntityNotFoundException {

        var episode = episodeRepository.findById(episodeId).orElseThrow(() -> new EntityNotFoundException(Episode.class));

        var history = historyRepository.findById(historyId).orElseThrow(() -> new EntityNotFoundException(History.class));

        var historyNode = mapper.convertValue(historyNodeDTO, HistoryNode.class);
        historyNode.setEpisode(episode);
        historyNode.setHistoryId(historyId);

        var createdHistoryNode = historyNodeRepository.save(historyNode);

        history.getNodes().add(createdHistoryNode);

        historyRepository.save(history);

        return mapper.convertValue(createdHistoryNode, HistoryNodeDTO.class);
    }

    public HistoryNodeDTO insert(HistoryNodeDTO historyNodeDTO, String movieId, String historyId) throws EntityNotFoundException {

        var movie = movieRepository.findById(movieId).orElseThrow(() -> new EntityNotFoundException(Movie.class));

        var history = historyRepository.findById(historyId).orElseThrow(() -> new EntityNotFoundException(History.class));

        var historyNode = mapper.convertValue(historyNodeDTO, HistoryNode.class);
        historyNode.setMovie(movie);
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
