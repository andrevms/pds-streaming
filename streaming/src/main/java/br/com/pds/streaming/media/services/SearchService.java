package br.com.pds.streaming.media.services;

import br.com.pds.streaming.mapper.modelMapper.MyModelMapper;
import br.com.pds.streaming.media.model.dto.MediaDTO;
import br.com.pds.streaming.media.model.dto.MovieDTO;
import br.com.pds.streaming.media.model.dto.TvShowDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class SearchService {

    private MovieService movieService;
    private TvShowService tvShowService;
    private MyModelMapper mapper;

    @Autowired
    public SearchService(MovieService movieService, TvShowService tvShowService, MyModelMapper mapper) {
        this.movieService = movieService;
        this.tvShowService = tvShowService;
        this.mapper = mapper;
    }

    public List<? extends MediaDTO> searchByTitle(String title) {

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

    public List<? extends MediaDTO> searchByCategory(String category) {

        List<MediaDTO> results = new ArrayList<>();

        var movies = movieService.findMovieByCategory(category);

        if (movies != null) {
            movies.forEach(movie -> {
                results.add(mapper.convertValue(movie, MovieDTO.class));
            });
        }

        var tvShows = tvShowService.findTvShowByCategory(category);

        if (tvShows != null) {
            tvShows.forEach(tvShow -> {
                results.add(mapper.convertValue(tvShow, TvShowDTO.class));
            });
        }

        return results;
    }

    public List<MovieDTO> getRandomMovies(int count) {

        var movies = movieService.findAll();
        Collections.shuffle(movies);

        movies = movies.subList(0, Math.min(count, movies.size()));

        List<MovieDTO> results = new ArrayList<>();
        if (movies != null) {
            movies.forEach(movie -> {
                results.add(mapper.convertValue(movie, MovieDTO.class));
            });
        }
        return results;
    }

    public List<TvShowDTO> getRandomTvShows(int count) {

        var tvShows = tvShowService.findAll();
        Collections.shuffle(tvShows);

        tvShows = tvShows.subList(0, Math.min(count, tvShows.size()));

        List<TvShowDTO> results = new ArrayList<>();
        if (tvShows != null) {
            tvShows.forEach(movie -> {
                results.add(mapper.convertValue(movie, TvShowDTO.class));
            });
        }
        return results;
    }
}
