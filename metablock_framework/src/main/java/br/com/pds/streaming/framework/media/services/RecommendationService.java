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

    @Autowired
    public RecommendationService(UserService userService) {
        this.userService = userService;
    }

    public List<? extends MediaDTO> getRecommendations(String userId) {
        setHistoryNodes(userId);
        calculatePopularity();
        hook();

        return generateRecommendations();
    }

    public abstract void setHistoryNodes(String userId);

    public abstract void calculatePopularity();

    public void hook() {}

    public abstract List<? extends MediaDTO> generateRecommendations();

}