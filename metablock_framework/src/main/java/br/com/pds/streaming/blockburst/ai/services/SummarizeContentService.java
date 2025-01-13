package br.com.pds.streaming.blockburst.ai.services;

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
public class SummarizeContentService {

    @Qualifier("ollamaAIService")
    @Autowired
    ChatService chatService;

    @Qualifier("assemblyAITranscribeService")
    @Autowired
    TranscriptionService transcriptionService;

    @Autowired
    TranscriptionRepository transcriptionRepository;

    public String summarizeContent(String source) {

        String chatPrompt = "Você deve agir como uma especialista no assunto filmes e serie e ira sumarizar o texto recebido : ";
        Transcription transcription = getTranscription(source);

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