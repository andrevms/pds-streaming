package br.com.pds.streaming.framework.ai.services.ollama;

import br.com.pds.streaming.framework.ai.services.AbstractSpringbootAiChatService;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OllamaAIService extends AbstractSpringbootAiChatService {
    @Autowired
    public OllamaAIService(OllamaChatModel chatModel) {
        super(chatModel);
    }

}

