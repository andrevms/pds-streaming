package br.com.pds.streaming.media.controllers;

import br.com.pds.streaming.exceptions.DuplicatedRatingException;
import br.com.pds.streaming.exceptions.ObjectNotFoundException;
import br.com.pds.streaming.media.model.dto.RatingDTO;
import br.com.pds.streaming.media.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/rating", "/api/ratings"})
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @GetMapping
    public ResponseEntity<List<RatingDTO>> getAllRatings() {
        return new ResponseEntity<>(ratingService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<RatingDTO>> getRatingsByUserId(@RequestParam(name = "userId") String userId) {
        return new ResponseEntity<>(ratingService.findByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RatingDTO> getRatingById(@PathVariable String id) throws ObjectNotFoundException {
        return new ResponseEntity<>(ratingService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RatingDTO> createRating(@RequestBody RatingDTO ratingDTO, @RequestParam(name = "userId") String userId, @RequestParam(name = "movieId", required = false) String movieId, @RequestParam(name = "tvShowId", required = false) String tvShowId) throws ObjectNotFoundException, DuplicatedRatingException {
        if (tvShowId != null) {
            return new ResponseEntity<>(ratingService.insert(ratingDTO, tvShowId, userId), HttpStatus.CREATED);
        }

        if (movieId != null) {
            return new ResponseEntity<>(ratingService.insert(movieId, ratingDTO, userId), HttpStatus.CREATED);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RatingDTO> updateRating(@RequestBody RatingDTO ratingDTO, @PathVariable String id) throws ObjectNotFoundException {
        return new ResponseEntity<>(ratingService.update(ratingDTO, id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RatingDTO> patchRating(@RequestBody RatingDTO ratingDTO, @PathVariable String id) throws ObjectNotFoundException {
        return new ResponseEntity<>(ratingService.patch(ratingDTO, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RatingDTO> deleteRating(@PathVariable String id) {
        ratingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
