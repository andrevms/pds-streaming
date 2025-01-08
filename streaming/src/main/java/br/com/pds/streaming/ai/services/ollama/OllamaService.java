package br.com.pds.streaming.ai.services.ollama;

import br.com.pds.streaming.ai.services.AbstractSpringbootAiChatService;
import br.com.pds.streaming.ai.services.ChatService;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OllamaService extends AbstractSpringbootAiChatService implements ChatService {
    @Autowired
    public OllamaService(OllamaChatModel chatModel) {
        super(chatModel);
    }
}
