package br.com.pds.streaming.blockburst.ai.services;

import br.com.pds.streaming.ai.services.IChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlockBurstService implements IChatService {

    @Autowired
    private SummarizeContentService summarizeContentService;

    @Autowired
    private QuestionMakerService questionMakerService;

    @Override
    public String askLlm(String source) {
        return summarizeContentService.summarizeContent(source);
    }

    @Override
    public String askLlmQuiz(String source) {
        return questionMakerService.createQuestion(source);
    }
}