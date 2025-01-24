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
public class ClassInsightSummaryService implements AskLlm {
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

        String chatPrompt = "Vou fornecer a transcrição de uma aula. Resuma os pontos principais discutidos, destacando os conceitos-chave, termos importantes e qualquer explicação relevante. Em seguida, forneça links ou referências para esses pontos, se aplicável, com base no conteúdo da aula. O resumo deve ser conciso, mas informativo, e pode incluir sugestões de tópicos adicionais ou recursos relacionados para estudo complementar. Se houver múltiplos conceitos importantes, organize o resumo em tópicos claros. : ";
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
