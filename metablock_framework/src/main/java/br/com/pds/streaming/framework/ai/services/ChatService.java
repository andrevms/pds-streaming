package br.com.pds.streaming.framework.ai.services;

public interface ChatService {
    public String getModel();
    public String askLlm(String chatPrompt, String question);
}