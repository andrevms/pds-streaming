package br.com.pds.streaming.blockburst.ai.services;

import br.com.pds.streaming.framework.ai.services.AskLlm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BlockburstAIService {

    @Autowired
    @Qualifier("summarizeContentService")
    private AskLlm summarizeContentService;

    @Autowired
    @Qualifier("questionMakerService")
    private AskLlm questionMakerService;

    public String askLlm(String source) {
        return summarizeContentService.askllm(source);
    }

    public String askLlmQuiz(String source) {
        return questionMakerService.askllm(source);
    }
}