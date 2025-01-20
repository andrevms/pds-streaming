package br.com.pds.streaming.blockfy.media.controllers;

import br.com.pds.streaming.blockfy.media.model.dto.MusicDTO;
import br.com.pds.streaming.blockfy.media.services.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/music")
public class MusicController {

    @Autowired
    private MusicService musicService;

    @GetMapping
    public ResponseEntity<List<MusicDTO>> getAllMusic() {
        return new ResponseEntity<>(musicService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MusicDTO> getMusicById(@PathVariable String id) {
        return new ResponseEntity<>(musicService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MusicDTO> createMusic(@RequestBody MusicDTO musicDTO) {
        return new ResponseEntity<>(musicService.insert(musicDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MusicDTO> updateMusic(@RequestBody MusicDTO musicDTO, @PathVariable String id) {
        return new ResponseEntity<>(musicService.update(musicDTO, id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MusicDTO> patchMusic(@RequestBody MusicDTO musicDTO, @PathVariable String id) {
        return new ResponseEntity<>(musicService.patch(musicDTO, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMusic(@PathVariable String id) {
        musicService.delete(id);
        return ResponseEntity.noContent().build();
    }
}