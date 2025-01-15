package br.com.pds.streaming.framework.ai.controllers;

import br.com.pds.streaming.framework.ai.model.SummarizeRequest;
import br.com.pds.streaming.framework.ai.services.IChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class ChatAIController {

    @Autowired
    @Qualifier("blockburstService")
    private IChatService chatService;

    @PostMapping("/ask-llm")
    public String askLlm( @RequestBody SummarizeRequest content) {
        return chatService.askLlm(content.getSource());
    }

    @PostMapping("/ask-llm-quiz")
    public String askLlmquiz( @RequestBody SummarizeRequest content) {
        return chatService.askLlmQuiz(content.getSource());
    }
}
