package br.com.pds.streaming.yulearn.ai.controller;

import br.com.pds.streaming.framework.ai.model.SummarizeRequest;
import br.com.pds.streaming.yulearn.ai.services.YulearnAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("yulearn")
@RestController
@RequestMapping(value = "/api")
public class YulearnChatController {

    @Autowired
    private YulearnAIService chatService;

    @PostMapping("/ask-llm")
    public String askLlm(@RequestBody SummarizeRequest content) {
        return chatService.askLlm(content.getSource());
    }

    @PostMapping("/ask-llm-quiz")
    public String askLlmquiz( @RequestBody SummarizeRequest content) {
        return chatService.askLlmQuiz(content.getSource());
    }

    @PostMapping("/ask-llm-quiz-recommended")
    public String askLlmquizRecommended( @RequestBody SummarizeRequest content) {
        return chatService.askLlmQuiz(content.getSource());
    }
}
