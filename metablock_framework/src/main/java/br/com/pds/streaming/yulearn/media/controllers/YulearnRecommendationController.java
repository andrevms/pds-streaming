package br.com.pds.streaming.yulearn.media.controllers;

import br.com.pds.streaming.framework.media.controllers.RecommendationController;
import br.com.pds.streaming.yulearn.media.services.YulearnRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/yulearn-recommendations")
public class YulearnRecommendationController extends RecommendationController {

    @Autowired
    public YulearnRecommendationController(YulearnRecommendationService yulearnRecommendationService) {
        super(yulearnRecommendationService);
    }
}
