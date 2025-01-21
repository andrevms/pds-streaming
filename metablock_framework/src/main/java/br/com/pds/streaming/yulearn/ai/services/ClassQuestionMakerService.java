package br.com.pds.streaming.yulearn.ai.services;

import br.com.pds.streaming.framework.ai.services.AskLlm;
import br.com.pds.streaming.framework.ai.services.ChatService;
import br.com.pds.streaming.framework.exceptions.EntityNotFoundException;
import br.com.pds.streaming.framework.exceptions.TranscriptionFailedException;
import br.com.pds.streaming.framework.transcription.model.dto.Transcription;
import br.com.pds.streaming.framework.transcription.model.dto.requests.TranscriptionRequest;
import br.com.pds.streaming.framework.transcription.model.dto.responses.TranscriptionResponse;
import br.com.pds.streaming.framework.transcription.repositories.TranscriptionRepository;
import br.com.pds.streaming.framework.transcription.services.TranscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ClassQuestionMakerService implements AskLlm {

    @Qualifier("ollamaAIService")
    @Autowired
    ChatService chatService;

    @Qualifier("assemblyAITranscribeService")
    @Autowired
    TranscriptionService transcriptionService;

    @Autowired
    TranscriptionRepository transcriptionRepository;

    @Override
    public String askllm(String content) {
        String chatPrompt = "Vou fornecer a transcrição de uma aula. Por favor, crie 5 a 10 questões com base no conteúdo da aula. As questões podem ser de múltipla escolha, verdadeiro/falso, ou abertas, dependendo do conteúdo abordado. Cada questão deve ser relevante para o tema discutido, cobrindo os conceitos principais, teorias, definições e exemplos dados durante a aula. Após cada pergunta, forneça as alternativas (se for de múltipla escolha) ou a resposta correta (se for uma questão aberta ou de verdadeiro/falso). : ";
        Transcription transcription = getTranscription(content);

        return chatService.askLlm(chatPrompt, transcription.getContent());
    }

    private Transcription getTranscription(String source) {
        Transcription transcription = transcriptionRepository.getTranscriptionBySource(source);

        if (transcription == null) {
            TranscriptionRequest transcriptionRequest = new TranscriptionRequest(source);
            TranscriptionResponse transcriptionResponse = null;
            try {
                transcriptionResponse = transcriptionService.transcribe(transcriptionRequest);
            } catch (TranscriptionFailedException e) {
                throw new RuntimeException(e);
            } catch (EntityNotFoundException e) {
                throw new RuntimeException(e);
            }
            transcription = new Transcription(source, transcriptionResponse.getContent());
            transcriptionRepository.save(transcription);
        }
        return transcription;
    }
}
