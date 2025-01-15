package br.com.pds.streaming.framework.media.services;

import br.com.pds.streaming.framework.authentication.services.UserService;
import br.com.pds.streaming.framework.media.model.dto.HistoryNodeDTO;
import br.com.pds.streaming.framework.media.model.dto.MediaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    public List<MediaDTO> getRecommendations(String userId) {
        setHistoryNodes(userId);
        calculatePopularity();
        hook();

        return generateRecommendations();
    }

    public void setHistoryNodes(String userId) {
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