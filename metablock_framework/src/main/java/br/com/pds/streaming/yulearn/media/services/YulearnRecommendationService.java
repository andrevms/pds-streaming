package br.com.pds.streaming.yulearn.media.services;

import br.com.pds.streaming.framework.authentication.services.UserService;
import br.com.pds.streaming.framework.media.model.dto.HistoryNodeDTO;
import br.com.pds.streaming.framework.media.services.RecommendationService;
import br.com.pds.streaming.yulearn.mapper.modelMapper.YulearnMapper;
import br.com.pds.streaming.yulearn.media.model.dto.VideoLessonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

        if (historyNodes.size() == 0) {
            while (true) {
                System.out.println("History nodes is empty");
            }
        }

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

        if (popularity.size() == 0) {
            while (true) {
                System.out.println("Popularity nodes is empty");
            }
        }


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