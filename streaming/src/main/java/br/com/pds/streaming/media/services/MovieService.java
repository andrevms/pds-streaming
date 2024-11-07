package br.com.pds.streaming.media.services;

import br.com.pds.streaming.exceptions.ObjectNotFoundException;
import br.com.pds.streaming.mapper.modelMapper.MyModelMapper;
import br.com.pds.streaming.media.model.dto.MovieDTO;
import br.com.pds.streaming.media.model.entities.Movie;
import br.com.pds.streaming.media.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private MovieRepository movieRepository;
    private MyModelMapper mapper;

    @Autowired
    public MovieService(MovieRepository movieRepository, MyModelMapper mapper) {
        this.movieRepository = movieRepository;
        this.mapper = mapper;
    }

    public List<MovieDTO> findAll() {

        var movies = movieRepository.findAll();

        var moviesDTO = mapper.convertList(movies, MovieDTO.class);

        moviesDTO.forEach(MovieDTO::setRatingsAverage);

        return moviesDTO;
    }

    public MovieDTO findById(String id) throws ObjectNotFoundException {

        var movie = movieRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Movie not found"));

        var movieDTO = mapper.convertValue(movie, MovieDTO.class);

        movieDTO.setRatingsAverage();

        return movieDTO;
    }

    public MovieDTO insert(MovieDTO movieDTO) {

        var createdMovie = movieRepository.save(mapper.convertValue(movieDTO, Movie.class));

        return mapper.convertValue(createdMovie, MovieDTO.class);
    }

    public MovieDTO update(MovieDTO movieDTO, String id) throws ObjectNotFoundException {

        var movie = movieRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Movie not found"));

        movie.setTitle(movieDTO.getTitle());
        movie.setDescription(movieDTO.getDescription());
        movie.setVideoUrl(movieDTO.getVideoUrl());
        movie.setThumbnailUrl(movieDTO.getThumbnailUrl());
        movie.setAnimationUrl(movieDTO.getAnimationUrl());

        var updatedMovie = movieRepository.save(movie);

        return mapper.convertValue(updatedMovie, MovieDTO.class);
    }

    public void delete(String id) {
        movieRepository.deleteById(id);
    }
}
