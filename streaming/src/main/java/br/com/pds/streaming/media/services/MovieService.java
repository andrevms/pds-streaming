package br.com.pds.streaming.media.services;

import br.com.pds.streaming.exceptions.ObjectNotFoundException;
import br.com.pds.streaming.media.model.entities.Movie;
import br.com.pds.streaming.media.repositories.MovieRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public Movie findById(String id) throws ObjectNotFoundException {
        Optional<Movie> movie = movieRepository.findById(id);
        return movie.orElseThrow(() -> new ObjectNotFoundException("Movie not found."));
    }

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    public Movie insert(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie update(String id, Movie movie) throws ObjectNotFoundException {
        if (!movieRepository.existsById(id)) {
            throw new ObjectNotFoundException("Movie not found.");
        }

        movie.setId(new ObjectId(id));
        return movieRepository.save(movie);
    }

    public void delete(String id) {
        movieRepository.deleteById(id);
    }
}
