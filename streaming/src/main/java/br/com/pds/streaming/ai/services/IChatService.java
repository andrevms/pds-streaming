package br.com.pds.streaming.ai.services;

public interface IChatService {
    public String askLlm(String source);
    public String askLlmQuiz(String source);
}
