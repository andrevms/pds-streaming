package br.com.pds.streaming.media.controllers;

import br.com.pds.streaming.exceptions.UserNotFoundException;
import br.com.pds.streaming.media.services.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("/user")
    public ResponseEntity<?> getRecommendations(@RequestParam("userId") String userId) {

        try {
            return ResponseEntity.ok().body(recommendationService.recommendMoviesAndShows(userId));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}