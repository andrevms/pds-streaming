package br.com.pds.streaming.yulearn.ai.services;

import br.com.pds.streaming.framework.ai.services.AskLlm;
import org.springframework.stereotype.Service;

@Service
public class ErrorBasedQuizRecommenderService implements AskLlm
{

    @Override
    public String askllm(String content) {
        return "";
    }
}
