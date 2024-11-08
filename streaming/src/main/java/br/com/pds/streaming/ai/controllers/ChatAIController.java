package br.com.pds.streaming.ai.controllers;

import br.com.pds.streaming.ai.model.SummarizeRequest;
import br.com.pds.streaming.ai.services.ollama.OllamaAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/")
public class ChatAIController {

    @Autowired
    OllamaAIService ollamaAIService;

    @GetMapping("/ask-llm")
    public String askLlm( @RequestBody SummarizeRequest content) {
        StringBuilder sb = new StringBuilder();
        sb.append("Assunto escolhido: ").append(content.getSubject()).append("\n");

        sb.append("\n\n-----------------------\n\n");
        sb.append("Modelo da Ollama: ").append(ollamaAIService.getModel()).append("\n");
        sb.append("Resposta da Ollama: ");
        try {
            sb.append(ollamaAIService.askLlm(content.getSubject(), content.getDescription()));
        }catch (Exception e) {
            sb.append(e.getMessage());
            sb.append("Erro...");
        }

        return sb.toString().replace("\n", "<br>");
    }

    @GetMapping("/ask-llm-quiz")
    public String askLlmquiz( @RequestBody SummarizeRequest content) {
        StringBuilder sb = new StringBuilder();
        sb.append("Assunto escolhido: ").append(content.getSubject()).append("\n");

        sb.append("\n\n-----------------------\n\n");
        sb.append("Modelo da Ollama: ").append(ollamaAIService.getModel()).append("\n");
        sb.append("Resposta da Ollama: ");
        try {
            sb.append(ollamaAIService.createQuiz(content.getSubject(), content.getDescription()));
        }catch (Exception e) {
            sb.append("Erro...");
        }

        return sb.toString().replace("\n", "<br>");
    }
}
