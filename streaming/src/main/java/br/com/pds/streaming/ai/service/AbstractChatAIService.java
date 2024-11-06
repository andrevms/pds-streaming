package br.com.pds.streaming.ai.service;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractChatAIService implements IChatAIService {

    public List<Message> createMessages(String subject, String question) {
        List<Message> messages = new ArrayList<>();
        StringBuilder systemConfigMessage = new StringBuilder(
                "Você deve agir como uma especialista no assunto \"" + subject + "\". Se a pergunta for sobre outro assunto, diga educadamente que não pode responder."
        );

        messages.add(new SystemMessage(systemConfigMessage.toString()));
        messages.add(new UserMessage(question));

        return messages;
    }
}
