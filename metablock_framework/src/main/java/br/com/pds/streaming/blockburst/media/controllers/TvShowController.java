package br.com.pds.streaming.blockburst.media.controllers;

import br.com.pds.streaming.blockburst.media.model.dto.TvShowRequest;
import br.com.pds.streaming.blockburst.media.model.dto.TvShowResponse;
import br.com.pds.streaming.blockburst.media.services.TvShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/tvshows", "/api/tv-shows", "/api/tv_shows"})
public class TvShowController {

    @Autowired
    private TvShowService tvShowService;

    @GetMapping
    public ResponseEntity<List<TvShowResponse>> getAllTvShows() {
        return new ResponseEntity<>(tvShowService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TvShowResponse> getTvShowById(@PathVariable String id) throws Throwable {
        return new ResponseEntity<>(tvShowService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TvShowResponse> createTvShow(@RequestBody TvShowRequest tvShowDTO) {
        return new ResponseEntity<>(tvShowService.insert(tvShowDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TvShowResponse> updateTvShow(@RequestBody TvShowRequest tvShowDTO, @PathVariable String id) throws Throwable {
        return new ResponseEntity<>(tvShowService.update(tvShowDTO, id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TvShowResponse> patchTvShow(@RequestBody TvShowRequest tvShowDTO, @PathVariable String id) throws Throwable {
        return new ResponseEntity<>(tvShowService.patch(tvShowDTO, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTvShow(@PathVariable String id) throws Throwable {
        tvShowService.delete(id);
        return ResponseEntity.noContent().build();
    }
}