package br.com.pds.streaming.config.cloud.assemblyai;

import com.assemblyai.api.AssemblyAI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AssemblyAIAccess {

    @Value(value = "${cloud.assembly-ai.key}")
    private String assemblyAiKey;

    @Bean
    public AssemblyAI assemblyAIClient() {
        AssemblyAI client = AssemblyAI.builder()
                .apiKey(assemblyAiKey)
                .build();

        return client;
    }
}