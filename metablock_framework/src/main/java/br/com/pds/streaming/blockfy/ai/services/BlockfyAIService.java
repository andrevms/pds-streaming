package br.com.pds.streaming.blockfy.ai.services;

import br.com.pds.streaming.framework.ai.services.AskLlm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BlockfyAIService {

    @Autowired
    @Qualifier("curiosityQuizService")
    private AskLlm curiosityQuizService;

    public String askLlm(String source) {
        return curiosityQuizService.askllm(source);
    }
}