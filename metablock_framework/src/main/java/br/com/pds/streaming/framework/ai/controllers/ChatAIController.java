package br.com.pds.streaming.framework.ai.controllers;

import br.com.pds.streaming.framework.ai.model.SummarizeRequest;
import br.com.pds.streaming.framework.ai.services.ChatService;
import br.com.pds.streaming.framework.ai.services.IChatService;
import br.com.pds.streaming.framework.exceptions.EntityNotFoundException;
import br.com.pds.streaming.framework.exceptions.TranscriptionFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class ChatAIController {

    @Autowired
    @Qualifier("blockBurstService")
    private IChatService chatService;

    @PostMapping("/ask-llm")
    public String askLlm( @RequestBody SummarizeRequest content) throws TranscriptionFailedException, EntityNotFoundException {
        return chatService.askLlm(content.getSource());
    }

    @PostMapping("/ask-llm-quiz")
    public String askLlmquiz( @RequestBody SummarizeRequest content) throws TranscriptionFailedException, EntityNotFoundException {
        return chatService.askLlmQuiz(content.getSource());
    }
}
