package br.com.pds.streaming.blockfy.ai.services;

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
public class CuriosityQuizService implements AskLlm {
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
        String chatPrompt = "Eu vou fornecer a letra de uma música. Baseado nela, crie um quiz com 5 perguntas sobre a banda, o autor da música e curiosidades relacionadas. As perguntas devem ter alternativas (pelo menos 4 opções de resposta) e uma resposta correta para cada pergunta. As perguntas podem ser sobre a história da banda, o significado das letras, os membros da banda, o autor da música ou fatos curiosos. Se possível, forneça também a resposta correta após cada pergunta.";
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
