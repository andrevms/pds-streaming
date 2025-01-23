package br.com.pds.streaming.blockburst.ai.services;

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
public class QuestionMakerService implements AskLlm {

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
        String chatPrompt = "Você deve agir como uma especialista no assunto de filmes e series. E criar 5 perguntas de multipla escolha sobre o texto e mostrar suas respostas :";

        return chatService.askLlm(chatPrompt, content);
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