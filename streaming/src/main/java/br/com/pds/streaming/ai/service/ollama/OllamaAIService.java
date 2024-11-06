package br.com.pds.streaming.ai.service.ollama;

import br.com.pds.streaming.ai.service.AbstractSpringbootAiChatAIService;
import br.com.pds.streaming.ai.service.IChatAIService;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OllamaAIService extends AbstractSpringbootAiChatAIService implements IChatAIService {
    @Autowired
    public OllamaAIService(OllamaChatModel chatModel) {
        super(chatModel);
    }
}

