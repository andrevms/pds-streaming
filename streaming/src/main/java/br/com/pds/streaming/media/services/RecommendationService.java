package br.com.pds.streaming.media.services;

import br.com.pds.streaming.authentication.services.UserService;
import br.com.pds.streaming.exceptions.EntityNotFoundException;
import br.com.pds.streaming.media.model.dto.HistoryNodeDTO;
import br.com.pds.streaming.media.model.dto.MediaDTO;
import br.com.pds.streaming.media.model.dto.MovieDTO;
import br.com.pds.streaming.media.model.dto.TvShowDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public abstract class RecommendationService {

    protected final static Integer LIMIT = 15;

    protected UserService userService;
    protected List<HistoryNodeDTO> historyNodes;
    protected Map<String, Integer> popularity;

    @Autowired
    public RecommendationService(UserService userService) {
        this.userService = userService;
    }

    public List<MediaDTO> getRecommendations(String userId) throws EntityNotFoundException {
        setHistoryNodes(userId);
        calculatePopularity();
        hook();

        return generateRecommendations();
    }

    public void setHistoryNodes(String userId) throws EntityNotFoundException {
        var user = userService.findById(userId);

        var history = user.getHistory();

        List<HistoryNodeDTO> allItems = history.getNodes();
        int size = allItems.size();
        historyNodes = allItems.subList(Math.max(size - LIMIT, 0), size);
    }

    public abstract void calculatePopularity();

    public void hook() {}

    public abstract List<MediaDTO> generateRecommendations();

}
