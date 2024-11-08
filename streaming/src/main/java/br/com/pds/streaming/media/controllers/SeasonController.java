package br.com.pds.streaming.media.controllers;

import br.com.pds.streaming.exceptions.ObjectNotFoundException;
import br.com.pds.streaming.media.model.dto.SeasonDTO;
import br.com.pds.streaming.media.services.SeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seasons")
public class SeasonController {

    @Autowired
    private SeasonService seasonService;

    @GetMapping
    public ResponseEntity<List<SeasonDTO>> getAllSeasons() {
        return new ResponseEntity<>(seasonService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/tvShow")
    public ResponseEntity<List<SeasonDTO>> getSeasonsByTvShowId(@RequestParam(name = "tvShowId") String tvShowId) {
        return new ResponseEntity<>(seasonService.findByTvShowId(tvShowId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeasonDTO> getSeasonById(@PathVariable String id) throws ObjectNotFoundException {
        return new ResponseEntity<>(seasonService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SeasonDTO> createSeason(@RequestBody SeasonDTO seasonDTO, @RequestParam(name = "tvShowId") String tvShowId) throws ObjectNotFoundException {
        return new ResponseEntity<>(seasonService.insert(seasonDTO, tvShowId), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeasonDTO> updateSeason(@RequestBody SeasonDTO seasonDTO, @PathVariable String id) throws ObjectNotFoundException {
        return new ResponseEntity<>(seasonService.update(seasonDTO, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeason(@PathVariable String id) {
        seasonService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
