package br.com.pds.streaming.media.controllers;

import br.com.pds.streaming.exceptions.*;
import br.com.pds.streaming.media.model.dto.MovieDTO;
import br.com.pds.streaming.media.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/movie", "/api/movies"})
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public ResponseEntity<List<MovieDTO>> getAllMovies() {
        return new ResponseEntity<>(movieService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable String id) throws EntityNotFoundException {
        return new ResponseEntity<>(movieService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MovieDTO> createMovie(@RequestBody MovieDTO movieDTO) throws InvalidAnimationException, InvalidVideoException, InvalidThumbnailException {
        return new ResponseEntity<>(movieService.insert(movieDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> updateMovie(@RequestBody MovieDTO movieDTO, @PathVariable String id) throws EntityNotFoundException, InvalidAnimationException, InvalidVideoException, InvalidThumbnailException {
        return new ResponseEntity<>(movieService.update(movieDTO, id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MovieDTO> patchMovie(@RequestBody MovieDTO movieDTO, @PathVariable String id) throws EntityNotFoundException, InvalidAnimationException, InvalidVideoException, InvalidThumbnailException {
        return new ResponseEntity<>(movieService.patch(movieDTO, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable String id) throws InvalidSourceException, EntityNotFoundException {
        movieService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
