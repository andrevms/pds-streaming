package br.com.pds.streaming.ai.controller;

import br.com.pds.streaming.ai.service.ollama.OllamaAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChatAIController {

    @Autowired
    OllamaAIService ollamaAIService;

    @GetMapping("ask-llm")
    public String askLlm( @RequestParam(name = "subject", required = true) String subject,
                          @RequestParam (name = "question", required = true) String question
    ) {
        StringBuilder sb = new StringBuilder();
        sb.append("Assunto escolhido: ").append(subject).append("\n");
        sb.append("Pergunta: ").append(question);

        sb.append("\n\n-----------------------\n\n");
        sb.append("Modelo da Ollama: ").append(ollamaAIService.getModel()).append("\n");
        sb.append("Resposta da Ollama: ");
        try {
            sb.append(ollamaAIService.askLlm(subject, question));
        }catch (Exception e) {
            sb.append("Erro...");
        }

        return sb.toString().replace("\n", "<br>");
    }
}
