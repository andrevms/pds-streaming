package br.com.pds.streaming.ai.services;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractChatAIService implements IChatAIService {

    public List<Message> createMessages(String subject, String question) {
        List<Message> messages = new ArrayList<>();
        StringBuilder systemConfigMessage = new StringBuilder(
                "Você deve agir como uma especialista no assunto \"" + subject + "\". e ira sumarizar o texto recebido."
        );

        messages.add(new SystemMessage(systemConfigMessage.toString()));
        messages.add(new UserMessage(question));

        return messages;
    }

    public List<Message> createMessagesQuiz(String subject, String text) {
        List<Message> messages = new ArrayList<>();
        StringBuilder systemConfigMessage = new StringBuilder(
                "Você deve agir como uma especialista no assunto \"" + subject + "\". E criar perguntas sobre o texto"
        );

        messages.add(new SystemMessage(systemConfigMessage.toString()));
        messages.add(new UserMessage(text));

        return messages;
    }
}
