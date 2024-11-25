package br.com.pds.streaming.media.services;

import br.com.pds.streaming.authentication.model.dto.domain.UserDTO;
import br.com.pds.streaming.authentication.services.UserService;
import br.com.pds.streaming.exceptions.UserNotFoundException;
import br.com.pds.streaming.media.model.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private final static Integer LIMIT = 15;
    @Autowired
    private UserService userService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private TvShowService tvShowService;

    public List<CollectableDTO> recommendMoviesAndShows(String userId) throws UserNotFoundException {

        var user = userService.findById(userId);

        List<HistoryNodeDTO> lastItems = getLastHistoryItems(user.getHistory(), LIMIT);

        Map<String, Integer> categoryPopularity = calculateCategoryPopularity(lastItems);

        Set<String> watchedItemIds = getWatchedItemIds(lastItems);

        List<CollectableDTO> recommendations = getMoviesAndShowsByCategories(categoryPopularity, watchedItemIds);

        return recommendations.size() > 10 ? recommendations.subList(0, 10) : recommendations;
    }

    private List<HistoryNodeDTO> getLastHistoryItems(HistoryDTO history, int limit) {
        List<HistoryNodeDTO> allItems = history.getNodes();
        int size = allItems.size();
        return allItems.subList(Math.max(size - limit, 0), size);
    }

    private Map<String, Integer> calculateCategoryPopularity(List<HistoryNodeDTO> lastItems) {
        Map<String, Integer> categoryCount = new HashMap<>();

        for (HistoryNodeDTO node : lastItems) {
            if (node instanceof HistoryNodeWithMovieDTO) {
                for (String category : ((HistoryNodeWithMovieDTO) node).getMovie().getCategories()) {
                    categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
                }
            } else if (node instanceof HistoryNodeWithEpisodeDTO) {
                for (String category : ((HistoryNodeWithEpisodeDTO) node).getEpisode().getCategories()) {
                    categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
                }
            }
        }

        return categoryCount.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }

    private Set<String> getWatchedItemIds(List<HistoryNodeDTO> lastItems) {
        Set<String> watchedIds = new HashSet<>();

        for (HistoryNodeDTO node : lastItems) {
            if (node instanceof HistoryNodeWithMovieDTO) {
                watchedIds.add(((HistoryNodeWithMovieDTO) node).getMovie().getId());
            } else if (node instanceof HistoryNodeWithEpisodeDTO) {
                watchedIds.add(((HistoryNodeWithEpisodeDTO) node).getEpisode().getId());
            }
        }

        return watchedIds;
    }

    private List<CollectableDTO> getMoviesAndShowsByCategories(Map<String, Integer> categoryPopularity, Set<String> watchedItemIds) {

        List<CollectableDTO> allMoviesAndShows = getAllMoviesAndShows();

        Set<String> recommendedItemIds = new HashSet<>();
        List<CollectableDTO> recommendedItems = new ArrayList<>();

        for (Map.Entry<String, Integer> categoryEntry : categoryPopularity.entrySet()) {
            String category = categoryEntry.getKey();

            for (CollectableDTO item : allMoviesAndShows) {
                if (item instanceof MovieDTO) {
                    MovieDTO movie = (MovieDTO) item;
                    if (!watchedItemIds.contains(movie.getId()) && !recommendedItemIds.contains(movie.getId()) && movie.getCategories().contains(category)) {
                        recommendedItems.add(movie);
                        recommendedItemIds.add(movie.getId());
                    }
                } else if (item instanceof TvShowDTO) {
                    TvShowDTO tvShow = (TvShowDTO) item;
                    if (!watchedItemIds.contains(tvShow.getId()) && !recommendedItemIds.contains(tvShow.getId()) && tvShow.getCategories().contains(category)) {
                        recommendedItems.add(tvShow);
                        recommendedItemIds.add(tvShow.getId());
                    }
                }
            }
        }

        return recommendedItems;
    }

    private List<CollectableDTO> getAllMoviesAndShows() {
        List<CollectableDTO> allItems = new ArrayList<>();

        var movies = movieService.findAll();
        var tvShows = tvShowService.findAll();

        allItems.addAll(movies);
        allItems.addAll(tvShows);

        return allItems;
    }
}
