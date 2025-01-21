package br.com.pds.streaming.yulearn.media.services;

import br.com.pds.streaming.blockburst.mapper.modelMapper.BlockburstMapper;
import br.com.pds.streaming.blockburst.media.model.dto.MovieResponse;
import br.com.pds.streaming.blockburst.media.model.dto.TvShowResponse;
import br.com.pds.streaming.blockburst.media.services.MovieService;
import br.com.pds.streaming.blockburst.media.services.TvShowService;
import br.com.pds.streaming.blockfy.mapper.modelMapper.BlockfyMapper;
import br.com.pds.streaming.blockfy.media.model.dto.MusicDTO;
import br.com.pds.streaming.framework.authentication.model.dto.domain.UserDTO;
import br.com.pds.streaming.framework.authentication.services.UserService;
import br.com.pds.streaming.framework.media.model.dto.HistoryDTO;
import br.com.pds.streaming.framework.media.model.dto.HistoryNodeDTO;
import br.com.pds.streaming.framework.media.model.dto.MediaDTO;
import br.com.pds.streaming.framework.media.model.entities.History;
import br.com.pds.streaming.framework.media.repositories.HistoryRepository;
import br.com.pds.streaming.framework.media.services.RecommendationService;
import br.com.pds.streaming.yulearn.mapper.modelMapper.YulearnMapper;
import br.com.pds.streaming.yulearn.media.model.dto.VideoLessonResponse;
import br.com.pds.streaming.yulearn.media.model.entities.VideoLesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class YulearnRecommendationService extends RecommendationService {

    private List<VideoLessonResponse> allVideoLessons;
    private VideoLessonService videoLessonService;
    protected Map<String, Integer> popularity;
    private YulearnMapper mapper;

    @Autowired
    public YulearnRecommendationService(UserService userService, YulearnMapper mapper, VideoLessonService videoLessonService) {
        super(userService);
        this.mapper = mapper;
        this.videoLessonService = videoLessonService;
    }

    @Override
    public void setHistoryNodes(String userId) {
        var dto = userService.findById(userId);
        var user = userService.loadUserByUsername(dto.getUsername());
        var history = user.getHistory();

        List<HistoryNodeDTO> allItems =  mapper.convertList(history.getNodes(), HistoryNodeDTO.class);
        int size = allItems.size();
        historyNodes = allItems.subList(0, Math.min(LIMIT, size));
    }

    @Override
    public void calculatePopularity() {
        Map<String, Integer> categoryCount = new HashMap<>();

        for (HistoryNodeDTO node : historyNodes) {
            for (String category : node.getMedia().getCategories()) {
                categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
            }
        }

        popularity = categoryCount.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }

    @Override
    public void hook() {
        allVideoLessons = videoLessonService.findAll();
    }

    @Override
    public List<VideoLessonResponse> generateRecommendations() {
        List<VideoLessonResponse> recommendedItems = new ArrayList<>();

        for (Map.Entry<String, Integer> categoryEntry : popularity.entrySet()) {
            String category = categoryEntry.getKey();

            List<VideoLessonResponse> filteredLessons = allVideoLessons.stream()
                    .filter(lesson -> lesson.getCategories() != null && lesson.getCategories().contains(category))
                    .collect(Collectors.toList());

            for (VideoLessonResponse lesson : filteredLessons) {
                if (!recommendedItems.contains(lesson)) {
                    recommendedItems.add(lesson);
                }
            }
        }

        return recommendedItems;
    }
}