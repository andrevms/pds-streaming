package br.com.pds.streaming.media.controllers;

import br.com.pds.streaming.exceptions.EntityNotFoundException;
import br.com.pds.streaming.media.services.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("/user")
    public ResponseEntity<?> getRecommendations(@RequestParam("userId") String userId) throws EntityNotFoundException {

        return ResponseEntity.ok().body(recommendationService.recommendMedia(userId));
    }
}