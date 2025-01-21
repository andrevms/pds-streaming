package br.com.pds.streaming.blockburst.ai.controller;

import br.com.pds.streaming.blockburst.ai.services.BlockburstAIService;
import br.com.pds.streaming.framework.ai.model.SummarizeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Profile("blockburst")
@RestController
@RequestMapping(value = "/api")
public class BlockBurstChatController {

    @Autowired
    private BlockburstAIService chatService;

    @PostMapping("/ask-llm")
    public String askLlm(@RequestBody SummarizeRequest content) {
        return chatService.askLlm(content.getSource());
    }

    @PostMapping("/ask-llm-quiz")
    public String askLlmquiz( @RequestBody SummarizeRequest content) {
        return chatService.askLlmQuiz(content.getSource());
    }
}
