package br.com.pds.streaming.framework.media.services;

import br.com.pds.streaming.framework.authentication.services.UserService;
import br.com.pds.streaming.framework.exceptions.EntityNotFoundException;
import br.com.pds.streaming.framework.media.model.dto.HistoryDTO;
import br.com.pds.streaming.framework.media.model.dto.HistoryNodeDTO;
import br.com.pds.streaming.framework.media.model.dto.MediaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private final static Integer LIMIT = 15;
    @Autowired
    private UserService userService;

    public List<MediaDTO> recommendMedia(String userId) throws EntityNotFoundException {

        var user = userService.findById(userId);

        List<HistoryNodeDTO> lastItems = getLastHistoryItems(user.getHistory(), LIMIT);

        Map<String, Integer> categoryPopularity = calculateCategoryPopularity(lastItems);

        Set<String> watchedItemIds = getWatchedItemIds(lastItems);

        List<MediaDTO> recommendations = getMediaByCategories(categoryPopularity, watchedItemIds);

        return recommendations.size() > 10 ? recommendations.subList(0, 10) : recommendations;
    }

    private List<HistoryNodeDTO> getLastHistoryItems(HistoryDTO history, int limit) {
        List<HistoryNodeDTO> allItems = history.getNodes();
        int size = allItems.size();
        return allItems.subList(Math.max(size - limit, 0), size);
    }

    private Map<String, Integer> calculateCategoryPopularity(List<HistoryNodeDTO> lastItems) {
        Map<String, Integer> categoryCount = new HashMap<>();

//        for (HistoryNodeDTO node : lastItems) {
//            if (node.getMedia() instanceof MovieDTO) {
//                for (String category : ((MovieDTO) node.getMedia()).getCategories()) {
//                    categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
//                }
//            } else if (node.getMedia() instanceof TvShowDTO) {
//                for (String category : ((TvShowDTO) node.getMedia()).getCategories()) {
//                    categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
//                }
//            }
//        }

        return categoryCount.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }

    private Set<String> getWatchedItemIds(List<HistoryNodeDTO> lastItems) {
        Set<String> watchedIds = new HashSet<>();

        for (HistoryNodeDTO node : lastItems) {
            watchedIds.add(node.getMedia().getId());
        }

        return watchedIds;
    }

    private List<MediaDTO> getMediaByCategories(Map<String, Integer> categoryPopularity, Set<String> watchedItemIds) {

        List<MediaDTO> allMoviesAndShows = getAllMoviesAndShows();

        Set<String> recommendedItemIds = new HashSet<>();
        List<MediaDTO> recommendedItems = new ArrayList<>();

        for (Map.Entry<String, Integer> categoryEntry : categoryPopularity.entrySet()) {
            String category = categoryEntry.getKey();

//            for (MediaDTO item : allMoviesAndShows) {
//                if (item instanceof MovieDTO) {
//                    MovieDTO movie = (MovieDTO) item;
//                    if (!watchedItemIds.contains(movie.getId()) && !recommendedItemIds.contains(movie.getId()) && movie.getCategories().contains(category)) {
//                        recommendedItems.add(movie);
//                        recommendedItemIds.add(movie.getId());
//                    }
//                } else if (item instanceof TvShowDTO) {
//                    TvShowDTO tvShow = (TvShowDTO) item;
//                    if (!watchedItemIds.contains(tvShow.getId()) && !recommendedItemIds.contains(tvShow.getId()) && tvShow.getCategories().contains(category)) {
//                        recommendedItems.add(tvShow);
//                        recommendedItemIds.add(tvShow.getId());
//                    }
//                }
//            }
        }

        return recommendedItems;
    }

    private List<MediaDTO> getAllMoviesAndShows() {
        List<MediaDTO> allItems = new ArrayList<>();

//        var movies = movieService.findAll();
//        var tvShows = tvShowService.findAll();
//
//        allItems.addAll(movies);
//        allItems.addAll(tvShows);

        return allItems;
    }
}
