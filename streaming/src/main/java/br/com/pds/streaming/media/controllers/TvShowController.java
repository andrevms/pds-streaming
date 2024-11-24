package br.com.pds.streaming.media.controllers;

import br.com.pds.streaming.exceptions.InvalidAnimationException;
import br.com.pds.streaming.exceptions.InvalidSourceException;
import br.com.pds.streaming.exceptions.InvalidThumbnailException;
import br.com.pds.streaming.exceptions.ObjectNotFoundException;
import br.com.pds.streaming.media.model.dto.TvShowDTO;
import br.com.pds.streaming.media.services.TvShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/tvshow", "/api/tvshows", "/api/tv-show", "/api/tv-shows", "/api/tv_show", "/api/tv_shows"})
public class TvShowController {

    @Autowired
    private TvShowService tvShowService;

    @GetMapping
    public ResponseEntity<List<TvShowDTO>> getAllTvShows() {
        return new ResponseEntity<>(tvShowService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TvShowDTO> getTvShowById(@PathVariable String id) throws ObjectNotFoundException {
        return new ResponseEntity<>(tvShowService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TvShowDTO> createTvShow(@RequestBody TvShowDTO tvShowDTO) throws InvalidThumbnailException, InvalidAnimationException {
        return new ResponseEntity<>(tvShowService.insert(tvShowDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TvShowDTO> updateTvShow(@RequestBody TvShowDTO tvShowDTO, @PathVariable String id) throws ObjectNotFoundException, InvalidThumbnailException, InvalidAnimationException {
        return new ResponseEntity<>(tvShowService.update(tvShowDTO, id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TvShowDTO> patchTvShow(@RequestBody TvShowDTO tvShowDTO, @PathVariable String id) throws ObjectNotFoundException, InvalidThumbnailException, InvalidAnimationException {
        return new ResponseEntity<>(tvShowService.patch(tvShowDTO, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTvShow(@PathVariable String id) throws InvalidSourceException, ObjectNotFoundException {
        tvShowService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
