package br.com.pds.streaming.yulearn.ai.services;

import br.com.pds.streaming.framework.ai.services.AskLlm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class YuLearnAIService
{
    @Autowired
    @Qualifier("classInsightSummaryService")
    private AskLlm classInsightSummaryService;

    @Autowired
    @Qualifier("classQuestionMakerService")
    private AskLlm classQuestionMakerService;

    @Autowired
    @Qualifier("errorBasedQuizRecommenderService")
    private AskLlm errorBasedQuizRecommenderService;

    public String askLlm(String source) {
        return classInsightSummaryService.askllm(source);
    }

    public String askLlmQuiz(String source) {
        return classQuestionMakerService.askllm(source);
    }

    public String askLlmQuizRecommended(String source) {
        return errorBasedQuizRecommenderService.askllm(source);
    }

}
