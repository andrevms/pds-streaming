package br.com.pds.streaming.blockburst.media.controllers;

import br.com.pds.streaming.blockburst.media.model.dto.MovieRequest;
import br.com.pds.streaming.blockburst.media.model.dto.MovieResponse;
import br.com.pds.streaming.blockburst.media.services.MovieService;
import br.com.pds.streaming.framework.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public ResponseEntity<List<MovieResponse>> getAllMovies() {
        return new ResponseEntity<>(movieService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> getMovieById(@PathVariable String id) throws EntityNotFoundException {
        return new ResponseEntity<>(movieService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MovieResponse> createMovie(@RequestBody MovieRequest movieRequest) throws InvalidAnimationException, InvalidVideoException, InvalidThumbnailException {
        return new ResponseEntity<>(movieService.insert(movieRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponse> updateMovie(@RequestBody MovieRequest movieRequest, @PathVariable String id) throws EntityNotFoundException, InvalidAnimationException, InvalidVideoException, InvalidThumbnailException {
        return new ResponseEntity<>(movieService.update(movieRequest, id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MovieResponse> patchMovie(@RequestBody MovieRequest movieRequest, @PathVariable String id) throws EntityNotFoundException, InvalidAnimationException, InvalidVideoException, InvalidThumbnailException {
        return new ResponseEntity<>(movieService.patch(movieRequest, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable String id) throws InvalidSourceException, EntityNotFoundException {
        movieService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
