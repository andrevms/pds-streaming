package br.com.pds.streaming.media.services;

import br.com.pds.streaming.mapper.modelMapper.MyModelMapper;
import br.com.pds.streaming.media.model.dto.MediaDTO;
import br.com.pds.streaming.media.model.dto.MovieDTO;
import br.com.pds.streaming.media.model.dto.SearchResultDTO;
import br.com.pds.streaming.media.model.dto.TvShowDTO;
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
    @Autowired
    private MyModelMapper mapper;

    public List<? extends MediaDTO> search(String title) {

        List<MediaDTO> results = new ArrayList<>();

        var movies = movieService.findMovieByTitle(title);

        if (movies != null) {
            movies.forEach(movie -> {
                results.add(mapper.convertValue(movie, MovieDTO.class));
            });
        }

        var tvShows = tvShowService.findTvShowByTitle(title);

        if (tvShows != null) {
            tvShows.forEach(tvShow -> {
                results.add(mapper.convertValue(tvShow, TvShowDTO.class));
            });
        }

        return results;
    }

}
