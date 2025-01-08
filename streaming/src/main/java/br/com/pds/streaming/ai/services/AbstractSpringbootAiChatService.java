package br.com.pds.streaming.ai.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;

public abstract class AbstractSpringbootAiChatService extends AbstractChatService implements ChatService {
    protected ChatModel chatModel;
    protected ChatClient chatClient;

    protected AbstractSpringbootAiChatService() {
    }

    protected AbstractSpringbootAiChatService(ChatModel chatModel) {
        this.chatModel = chatModel;
        this.chatClient = ChatClient.builder(chatModel).build();
    }

    @Override
    public String getModel() {
        String model = null;
        if(this.chatModel != null){
            model = this.chatModel.getDefaultOptions().getModel();
        }
        return model;
    }

    @Override
    public String askLlm(String chatPrompt, String question) {
        return chatClient.prompt()
                .messages(
                        createMessages(chatPrompt, question)
                ).call()
                .content();
    }
}
