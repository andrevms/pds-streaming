package br.com.pds.streaming.blockfy.ai.controller;

import br.com.pds.streaming.blockfy.ai.services.BlockfyAIService;
import br.com.pds.streaming.framework.ai.model.SummarizeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Profile("blockfy")
@RestController
@RequestMapping(value = "/api")
public class BlockfyChatController {
    @Autowired
    @Qualifier("blockfyAIService")
    private BlockfyAIService chatService;

    @PostMapping("/ask-llm")
    public String askLlm(@RequestBody SummarizeRequest content) {
        return chatService.askLlm(content.getSource());
    }
}
