package br.com.pds.streaming.framework.ai.services;

public interface IChatService {
    String askLlm(String source);
    String askLlmQuiz(String source);
}
