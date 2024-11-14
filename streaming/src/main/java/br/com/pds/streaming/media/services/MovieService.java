package br.com.pds.streaming.media.services;

import br.com.pds.streaming.exceptions.ObjectNotFoundException;
import br.com.pds.streaming.mapper.modelMapper.MyModelMapper;
import br.com.pds.streaming.media.model.dto.MovieDTO;
import br.com.pds.streaming.media.model.entities.Movie;
import br.com.pds.streaming.media.model.entities.Rating;
import br.com.pds.streaming.media.repositories.MovieRepository;
import br.com.pds.streaming.media.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private MovieRepository movieRepository;
    private RatingRepository ratingRepository;
    private MyModelMapper mapper;

    @Autowired
    public MovieService(MovieRepository movieRepository, RatingRepository ratingRepository, MyModelMapper mapper) {
        this.movieRepository = movieRepository;
        this.ratingRepository = ratingRepository;
        this.mapper = mapper;
    }

    public List<MovieDTO> findAll() {

        var movies = movieRepository.findAll();

        var moviesDTO = mapper.convertList(movies, MovieDTO.class);

        moviesDTO.forEach(x -> {
            var movieRatings = ratingRepository.findAll().stream().filter(r -> r.getMovieId() != null).filter(r -> r.getMovieId().equals(x.getId())).toList();
            x.setRatingsAverage(!movieRatings.isEmpty() ? String.format("%.1f", movieRatings.stream().mapToDouble(Rating::getStars).sum() / movieRatings.size()) : "Não há avaliações para este filme.");
        });

        return moviesDTO;
    }

    public MovieDTO findById(String id) throws ObjectNotFoundException {

        var movie = movieRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(Movie.class));

        var movieDTO = mapper.convertValue(movie, MovieDTO.class);

        return refreshRatingsAverage(movieDTO, id);
    }

    public MovieDTO insert(MovieDTO movieDTO) {

        var createdMovie = movieRepository.save(mapper.convertValue(movieDTO, Movie.class));

        return mapper.convertValue(createdMovie, MovieDTO.class); // To be reviewed later
    }

    public MovieDTO update(MovieDTO movieDTO, String id) throws ObjectNotFoundException {

        var movie = movieRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(Movie.class));

        movie.setTitle(movieDTO.getTitle());
        movie.setDescription(movieDTO.getDescription());
        movie.setVideoUrl(movieDTO.getVideoUrl());
        movie.setThumbnailUrl(movieDTO.getThumbnailUrl());
        movie.setAnimationUrl(movieDTO.getAnimationUrl());

        var updatedMovie = movieRepository.save(movie);

        return refreshRatingsAverage(mapper.convertValue(updatedMovie, MovieDTO.class), id); // To be reviewed later
    }

    public MovieDTO patch(MovieDTO movieDTO, String id) throws ObjectNotFoundException {

        var movie = movieRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(Movie.class));

        if (movieDTO.getTitle() != null) {
            movie.setTitle(movieDTO.getTitle());
        }

        if (movieDTO.getDescription() != null) {
            movie.setDescription(movieDTO.getDescription());
        }

        if (movieDTO.getVideoUrl() != null) {
            movie.setVideoUrl(movieDTO.getVideoUrl());
        }

        if (movieDTO.getThumbnailUrl() != null) {
            movie.setThumbnailUrl(movieDTO.getThumbnailUrl());
        }

        if (movieDTO.getAnimationUrl() != null) {
            movie.setAnimationUrl(movieDTO.getAnimationUrl());
        }

        var patchedMovie = movieRepository.save(movie);

        return refreshRatingsAverage(mapper.convertValue(patchedMovie, MovieDTO.class), id); // To be reviewed later
    }

    public void delete(String id) {

        deleteOrphanRatings(id);

        movieRepository.deleteById(id);
    }

    private void deleteOrphanRatings(String movieId) {

        var ratings = ratingRepository.findByMovieId(movieId);

        ratingRepository.deleteAll(ratings);
    }

    private MovieDTO refreshRatingsAverage(MovieDTO movieDTO, String id) {

        var movieRatings = ratingRepository.findAll().stream().filter(r -> r.getMovieId() != null).filter(r -> r.getMovieId().equals(id)).toList();

        movieDTO.setRatingsAverage(!movieRatings.isEmpty() ? String.format("%.1f", movieRatings.stream().mapToDouble(Rating::getStars).sum() / movieRatings.size()) : "Não há avaliações para este filme.");

        return movieDTO; // To be reviewed later
    }
}