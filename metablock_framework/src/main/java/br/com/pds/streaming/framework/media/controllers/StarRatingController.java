package br.com.pds.streaming.framework.media.controllers;

import br.com.pds.streaming.framework.exceptions.DuplicatedRatingException;
import br.com.pds.streaming.framework.exceptions.EntityNotFoundException;
import br.com.pds.streaming.framework.media.model.dto.StarRatingDTO;
import br.com.pds.streaming.framework.media.services.StarRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/api/starratings", "/api/star-ratings", "/api/star_ratings"})
public class StarRatingController {

    @Autowired
    private StarRatingService starRatingService;

    @GetMapping
    public ResponseEntity<List<StarRatingDTO>> getAllStarRatings() {
        return new ResponseEntity<>(starRatingService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<StarRatingDTO>> getAllStarRatingsByUserId(@RequestParam(name = "userId") String userId) {
        return new ResponseEntity<>(starRatingService.findByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StarRatingDTO> getStarRatingById(@PathVariable String id) {
        return new ResponseEntity<>(starRatingService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StarRatingDTO> createStarRating(@RequestBody StarRatingDTO starRatingDTO, @RequestParam(name = "userId") String userId, @RequestParam(name = "mediaId") String mediaId) throws EntityNotFoundException, DuplicatedRatingException {
        return new ResponseEntity<>(starRatingService.insert(userId, mediaId, starRatingDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StarRatingDTO> updateStarRating(@RequestBody StarRatingDTO starRatingDTO, @PathVariable("id") String id) throws EntityNotFoundException {
        return new ResponseEntity<>(starRatingService.update(starRatingDTO, id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<StarRatingDTO> patchStarRating(@RequestBody StarRatingDTO starRatingDTO, @PathVariable("id") String id) throws EntityNotFoundException {
        return new ResponseEntity<>(starRatingService.patch(starRatingDTO, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStarRating(@PathVariable String id) {
        starRatingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
