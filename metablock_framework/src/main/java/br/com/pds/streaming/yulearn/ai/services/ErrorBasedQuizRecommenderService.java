package br.com.pds.streaming.yulearn.ai.services;

import br.com.pds.streaming.framework.ai.services.AskLlm;
import br.com.pds.streaming.framework.ai.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ErrorBasedQuizRecommenderService implements AskLlm
{
    @Qualifier("ollamaAIService")
    @Autowired
    ChatService chatService;

    @Override
    public String askllm(String content) {
        String chatPrompt = "Vou fornecer uma lista de questões. Crie 5 a 10 questões com base no conteúdo dessas questões. eAs questões podem ser de múltipla escolha, verdadeiro/falso, ou abertas, dependendo do conteúdo abordado. Cada questão deve ser relevante para o tema discutido da questão, cobrindo os conceitos principais, teorias, definições. Após cada pergunta, forneça as alternativas (se for de múltipla escolha) ou a resposta correta (se for uma questão aberta ou de verdadeiro/falso). : ";


        return chatService.askLlm(chatPrompt, content);
    }
}
