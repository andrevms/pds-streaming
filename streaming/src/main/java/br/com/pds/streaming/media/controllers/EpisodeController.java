package br.com.pds.streaming.media.controllers;

import br.com.pds.streaming.exceptions.ObjectNotFoundException;
import br.com.pds.streaming.media.model.dto.EpisodeDTO;
import br.com.pds.streaming.media.services.EpisodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/episodes")
public class EpisodeController {

    @Autowired
    private EpisodeService episodeService;

    @GetMapping
    public ResponseEntity<List<EpisodeDTO>> getAllEpisodes() {
        return new ResponseEntity<>(episodeService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/season")
    public ResponseEntity<List<EpisodeDTO>> getEpisodesBySeasonId(@RequestParam(name = "seasonId") String seasonId) {
        return new ResponseEntity<>(episodeService.findBySeasonId(seasonId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EpisodeDTO> getEpisodeById(@PathVariable String id) throws ObjectNotFoundException {
        return new ResponseEntity<>(episodeService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EpisodeDTO> createEpisode(@RequestBody EpisodeDTO episodeDTO, @RequestParam(name = "seasonId") String seasonId) throws ObjectNotFoundException {
        return new ResponseEntity<>(episodeService.insert(episodeDTO, seasonId), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EpisodeDTO> updateEpisode(@RequestBody EpisodeDTO episodeDTO, @PathVariable String id) throws ObjectNotFoundException {
        return new ResponseEntity<>(episodeService.update(episodeDTO, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEpisode(@PathVariable String id) {
        episodeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
