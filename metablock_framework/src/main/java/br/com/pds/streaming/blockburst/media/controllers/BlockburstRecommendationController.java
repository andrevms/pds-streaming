package br.com.pds.streaming.blockburst.media.controllers;

import br.com.pds.streaming.blockburst.media.services.BlockburstRecommendationService;
import br.com.pds.streaming.framework.media.controllers.RecommendationController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/blockburst-recommendations")
public class BlockburstRecommendationController extends RecommendationController {

    @Autowired
    public BlockburstRecommendationController(BlockburstRecommendationService blockburstRecommendationService) {
        super(blockburstRecommendationService);
    }
}
