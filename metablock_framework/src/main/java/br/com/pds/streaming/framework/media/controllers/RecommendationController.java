package br.com.pds.streaming.framework.media.controllers;

import br.com.pds.streaming.blockburst.media.services.BlockburstRecommendationService;
import br.com.pds.streaming.framework.media.services.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


public class RecommendationController {

    private RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/user")
    public ResponseEntity<?> getRecommendations(@RequestParam("userId") String userId) {

        return ResponseEntity.ok().body(recommendationService.getRecommendations(userId));
    }
}