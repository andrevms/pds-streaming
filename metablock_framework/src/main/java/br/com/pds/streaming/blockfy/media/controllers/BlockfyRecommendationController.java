package br.com.pds.streaming.blockfy.media.controllers;

import br.com.pds.streaming.blockfy.media.services.BlockfyRecommendationService;
import br.com.pds.streaming.framework.media.controllers.RecommendationController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/blockfy-recommendations")
public class BlockfyRecommendationController extends RecommendationController {

    @Autowired
    public BlockfyRecommendationController(BlockfyRecommendationService blockfyRecommendationService) {
        super(blockfyRecommendationService);
    }
}
