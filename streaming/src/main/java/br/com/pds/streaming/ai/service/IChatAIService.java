package br.com.pds.streaming.ai.service;

public interface IChatAIService {
    String getModel();
    String askLlm(String subject, String question);
    String createQuiz(String subject, String question);
}
