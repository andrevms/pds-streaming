package br.com.pds.streaming.framework.ai.services;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractChatService implements ChatService {
    public List<Message> createMessages(String chatPrompt, String question) {
        List<Message> messages = new ArrayList<>();
        messages.add(new SystemMessage(chatPrompt));
        messages.add(new UserMessage(question));

        return messages;
    }
}