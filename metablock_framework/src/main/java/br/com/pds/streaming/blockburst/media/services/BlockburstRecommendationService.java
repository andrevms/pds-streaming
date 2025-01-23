package br.com.pds.streaming.blockburst.media.services;

import br.com.pds.streaming.blockburst.mapper.modelMapper.BlockburstMapper;
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

@Service
public class BlockburstRecommendationService extends RecommendationService {

    private MovieService movieService;
    private TvShowService tvShowService;
    private Set<String> watchedMediaIds;
    protected Map<String, Integer> popularity;
    private BlockburstMapper mapper;

    @Autowired
    public BlockburstRecommendationService(UserService userService, MovieService movieService, TvShowService tvShowService, BlockburstMapper mapper) {
        super(userService);
        this.movieService = movieService;
        this.tvShowService = tvShowService;
        this.mapper = mapper;
    }

    @Override
    public void setHistoryNodes(String userId) {
        var dto = userService.findById(userId);
        var user = userService.loadUserByUsername(dto.getUsername());
        var history = user.getHistory();

        List<HistoryNodeDTO> allItems =  mapper.convertList(history.getNodes(), HistoryNodeDTO.class);
        int size = allItems.size();
        this.historyNodes = allItems.subList(Math.max(size - LIMIT, 0), size);
    }

    @Override
    public void calculatePopularity() {
        Map<String, Integer> categoryCount = new HashMap<>();

        for (HistoryNodeDTO node : historyNodes) {
            for (String category : node.getMedia().getCategories()) {
                categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
            }
        }

        this.popularity = categoryCount.entrySet().stream()
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

        List<MovieResponse> movies = movieService.findAll();
        List<TvShowResponse> tvShowResponses = tvShowService.findAll();

        Set<String> recommendedItemIds = new HashSet<>();
        List<MediaDTO> recommendedItems = new ArrayList<>();

        for (Map.Entry<String, Integer> categoryEntry : popularity.entrySet()) {
            String category = categoryEntry.getKey();

            for (MovieResponse item : movies) {
                MovieResponse movie = item;
                if (!watchedMediaIds.contains(movie.getId()) && !recommendedItemIds.contains(movie.getId()) && movie.getCategories().contains(category)) {
                    recommendedItems.add(movie);
                    recommendedItemIds.add(movie.getId());
                }
            }

            for (TvShowResponse item : tvShowResponses) {
                TvShowResponse tvShow = item;
                if (!watchedMediaIds.contains(tvShow.getId()) && !recommendedItemIds.contains(tvShow.getId()) && tvShow.getCategories().contains(category)) {
                    recommendedItems.add(tvShow);
                    recommendedItemIds.add(tvShow.getId());
                }
            }
        }


        return recommendedItems.size() > 10 ? recommendedItems.subList(0, 10) : recommendedItems;
    }

}