package br.com.pds.streaming.media.controllers;

import br.com.pds.streaming.exceptions.DuplicatedRatingException;
import br.com.pds.streaming.exceptions.EntityNotFoundException;
import br.com.pds.streaming.media.model.dto.LikeRatingDTO;
import br.com.pds.streaming.media.services.LikeRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/api/likeratings", "/api/like-ratings", "/api/like_ratings"})
public class LikeRatingController {

    @Autowired
    private LikeRatingService likeRatingService;

    @GetMapping
    public ResponseEntity<List<LikeRatingDTO>> getAllLikeRatings() {
        return new ResponseEntity<>(likeRatingService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<LikeRatingDTO>> getAllLikeRatingsByUserId(@RequestParam(name = "userId") String userId) {
        return new ResponseEntity<>(likeRatingService.findByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LikeRatingDTO> getLikeRatingById(@PathVariable String id) {
        return new ResponseEntity<>(likeRatingService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LikeRatingDTO> createLikeRating(@RequestBody LikeRatingDTO likeRatingDTO, @RequestParam(name = "userId") String userId, @RequestParam(name = "mediaId") String mediaId) throws EntityNotFoundException, DuplicatedRatingException {
        return new ResponseEntity<>(likeRatingService.insert(userId, mediaId, likeRatingDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LikeRatingDTO> updateLikeRating(@RequestBody LikeRatingDTO likeRatingDTO, @PathVariable String id) throws EntityNotFoundException {
        return new ResponseEntity<>(likeRatingService.update(likeRatingDTO, id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<LikeRatingDTO> patchLikeRating(@RequestBody LikeRatingDTO likeRatingDTO, @PathVariable String id) throws EntityNotFoundException {
        return new ResponseEntity<>(likeRatingService.patch(likeRatingDTO, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLikeRating(@PathVariable String id) {
        likeRatingService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
