package br.com.pds.streaming.framework.ai.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;

public abstract class AbstractSpringbootAiChatAIService extends AbstractChatAIService implements IChatAIService {
    protected ChatModel chatModel;
    protected ChatClient chatClient;

    protected AbstractSpringbootAiChatAIService() {
    }

    protected AbstractSpringbootAiChatAIService(ChatModel chatModel) {
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
    public String askLlm(String subject, String question) {
        return chatClient.prompt()
                .messages(
                        createMessages(subject, question)
                ).call()
                .content();
    }

    @Override
    public String createQuiz(String subject, String question) {
        return chatClient.prompt()
                .messages(
                        createMessagesQuiz(subject, question)
                ).call()
                .content();
    }
}
