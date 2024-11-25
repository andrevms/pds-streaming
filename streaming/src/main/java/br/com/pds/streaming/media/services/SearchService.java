package br.com.pds.streaming.media.services;

import br.com.pds.streaming.media.model.dto.SearchResultDTO;
import io.micrometer.core.instrument.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    @Autowired
    private MovieService movieService;
    @Autowired
    private TvShowService tvShowService;

    public List<SearchResultDTO> search(String title) {

        List<SearchResultDTO> searchResultDTOS = new ArrayList<>();
        var movies = movieService.findMovieByTitle(title);

        System.out.println("movies = " + movies.size());
        if (movies != null) {
            movies.forEach(movie -> {
                SearchResultDTO searchResultDTO = new SearchResultDTO();

                searchResultDTO.setId(movie.getId());
                searchResultDTO.setTitle(movie.getTitle());
                searchResultDTO.setDescription(movie.getDescription());
                searchResultDTO.setThumbnailUrl(movie.getThumbnailUrl());
                searchResultDTO.setAnimationUrl(movie.getAnimationUrl());

                searchResultDTOS.add(searchResultDTO);
            });
        }

        var tvShows = tvShowService.findTvShowByTitle(title);

        if (tvShows != null) {
            tvShows.forEach(tvShow -> {
                SearchResultDTO searchResultDTO = new SearchResultDTO();

                searchResultDTO.setId(tvShow.getId());
                searchResultDTO.setTitle(tvShow.getTitle());
                searchResultDTO.setDescription(tvShow.getDescription());
                searchResultDTO.setThumbnailUrl(tvShow.getThumbnailUrl());
                searchResultDTO.setAnimationUrl(tvShow.getAnimationUrl());

                searchResultDTOS.add(searchResultDTO);
            });
        }

        return searchResultDTOS;
    }

}
