package br.com.pds.streaming.framework.ai.services.ollama;

import br.com.pds.streaming.framework.ai.services.AbstractSpringbootAiChatService;
import br.com.pds.streaming.framework.ai.services.IChatService;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OllamaAIService extends AbstractSpringbootAiChatService implements IChatService {
    @Autowired
    public OllamaAIService(OllamaChatModel chatModel) {
        super(chatModel);
    }

    @Override
    public String askLlm(String source) {
        return "";
    }

    @Override
    public String askLlmQuiz(String source) {
        return "";
    }
}

