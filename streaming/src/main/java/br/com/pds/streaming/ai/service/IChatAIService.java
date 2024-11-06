package br.com.pds.streaming.ai.service;

public interface IChatAIService {
    public String getModel();
    public String askLlm(String subject, String question);
    public String createQuiz(String subject, String question);

}
