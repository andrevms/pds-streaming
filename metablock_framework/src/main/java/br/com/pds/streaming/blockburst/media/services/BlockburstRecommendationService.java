package br.com.pds.streaming.blockburst.media.services;

import br.com.pds.streaming.blockburst.media.model.dto.MovieResponse;
import br.com.pds.streaming.blockburst.media.model.dto.TvShowResponse;
import br.com.pds.streaming.framework.authentication.services.UserService;
import br.com.pds.streaming.framework.media.model.dto.HistoryNodeDTO;
import br.com.pds.streaming.framework.media.model.dto.MediaDTO;
import br.com.pds.streaming.framework.media.services.RecommendationService;
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
import java.util.stream.Stream;

@Service
public class BlockburstRecommendationService extends RecommendationService {

    private MovieService movieService;
    private TvShowService tvShowService;
    private Set<String> watchedMediaIds;

    @Autowired
    public BlockburstRecommendationService(UserService userService, MovieService movieService, TvShowService tvShowService) {
        super(userService);
        this.movieService = movieService;
        this.tvShowService = tvShowService;
    }

    @Override
    public void calculatePopularity() {
        Map<String, Integer> categoryCount = new HashMap<>();

        for (HistoryNodeDTO node : historyNodes) {
            if (node.getMedia() instanceof MovieResponse) {
                for (String category : node.getMedia().getCategories()) {
                    categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
                }
            } else if (node.getMedia() instanceof TvShowResponse) {
                for (String category : node.getMedia().getCategories()) {
                    categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
                }
            }
        }

        popularity = categoryCount.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }

    @Override
    public void hook() {
        Set<String> watched = new HashSet<>();

        for (HistoryNodeDTO node : historyNodes) {
            watched.add(node.getMedia().getId());
        }

        watchedMediaIds = watched;
    }

    @Override
    public List<MediaDTO> generateRecommendations() {

        List<MediaDTO> allMoviesAndShows = Stream.concat(
                movieService.findAll().stream(),
                tvShowService.findAll().stream()
        ).collect(Collectors.toList());

        Set<String> recommendedItemIds = new HashSet<>();
        List<MediaDTO> recommendedItems = new ArrayList<>();

        for (Map.Entry<String, Integer> categoryEntry : popularity.entrySet()) {
            String category = categoryEntry.getKey();

            for (MediaDTO item : allMoviesAndShows) {
                if (item instanceof MovieResponse) {
                    MovieResponse movie = (MovieResponse) item;
                    if (!watchedMediaIds.contains(movie.getId()) && !recommendedItemIds.contains(movie.getId()) && movie.getCategories().contains(category)) {
                        recommendedItems.add(movie);
                        recommendedItemIds.add(movie.getId());
                    }
                } else if (item instanceof TvShowResponse) {
                    TvShowResponse tvShow = (TvShowResponse) item;
                    if (!watchedMediaIds.contains(tvShow.getId()) && !recommendedItemIds.contains(tvShow.getId()) && tvShow.getCategories().contains(category)) {
                        recommendedItems.add(tvShow);
                        recommendedItemIds.add(tvShow.getId());
                    }
                }
            }
        }

        return recommendedItems.size() > 10 ? recommendedItems.subList(0, 10) : recommendedItems;
    }

}