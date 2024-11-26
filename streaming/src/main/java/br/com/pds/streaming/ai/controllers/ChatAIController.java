package br.com.pds.streaming.ai.controllers;

import br.com.pds.streaming.ai.model.SummarizeRequest;
import br.com.pds.streaming.ai.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/")
public class ChatAIController {

    @Autowired
    ChatService chatService;

    @GetMapping("/ask-llm")
    public String askLlm( @RequestBody SummarizeRequest content) {
        return chatService.askLlm(content.getSubject(), content.getDescription());
    }

    @GetMapping("/ask-llm-quiz")
    public String askLlmquiz( @RequestBody SummarizeRequest content) {
        return chatService.askLlmquiz(content.getSubject(), content.getDescription());
    }
}
