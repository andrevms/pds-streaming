package br.com.pds.streaming.ai.controllers;

import br.com.pds.streaming.ai.model.SummarizeRequest;
import br.com.pds.streaming.ai.services.QuestionMakerService;
import br.com.pds.streaming.ai.services.SummarizeContentService;
import br.com.pds.streaming.ai.services.ollama.OllamaService;
import br.com.pds.streaming.exceptions.EntityNotFoundException;
import br.com.pds.streaming.exceptions.TranscriptionFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class ChatAIController {

    @Autowired
    private SummarizeContentService summarizeContentService;

    @Autowired
    private QuestionMakerService questionMakerService;

    @PostMapping("/ask-llm")
    public String askLlm( @RequestBody SummarizeRequest content) throws TranscriptionFailedException, EntityNotFoundException {
        return summarizeContentService.summarizeContent(content.getSource());
    }

    @PostMapping("/ask-llm-quiz")
    public String askLlmquiz( @RequestBody SummarizeRequest content) throws TranscriptionFailedException, EntityNotFoundException {
        return questionMakerService.createQuestion(content.getSource());
    }
}
