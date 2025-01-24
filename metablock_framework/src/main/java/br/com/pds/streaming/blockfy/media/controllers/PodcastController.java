package br.com.pds.streaming.blockfy.media.controllers;

import br.com.pds.streaming.blockfy.media.model.dto.PodcastDTO;
import br.com.pds.streaming.blockfy.media.services.PodcastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/podcasts")
public class PodcastController {

    @Autowired
    private PodcastService podcastService;

    @GetMapping
    public ResponseEntity<List<PodcastDTO>> getAllPodcasts() {
        return new ResponseEntity<>(podcastService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PodcastDTO> getPodcastById(@PathVariable String id) {
        return new ResponseEntity<>(podcastService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PodcastDTO> createPodcast(@RequestBody PodcastDTO podcast) {
        return new ResponseEntity<>(podcastService.insert(podcast), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PodcastDTO> updatePodcast(@RequestBody PodcastDTO podcastDTO, @PathVariable String id) {
        return new ResponseEntity<>(podcastService.update(podcastDTO, id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PodcastDTO> patchPodcast(@RequestBody PodcastDTO podcastDTO, @PathVariable String id) {
        return new ResponseEntity<>(podcastService.patch(podcastDTO, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePodcast(@PathVariable String id) {
        podcastService.delete(id);
        return ResponseEntity.noContent().build();
    }
}