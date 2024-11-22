package br.com.pds.streaming.ai.services;

import br.com.pds.streaming.transcription.model.dto.Transcription;
import br.com.pds.streaming.transcription.model.dto.requests.TranscriptionRequest;
import br.com.pds.streaming.transcription.model.dto.responses.TranscriptionResponse;
import br.com.pds.streaming.transcription.repository.TranscriptionRepository;
import br.com.pds.streaming.transcription.services.TranscriptionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    @Qualifier("ollamaAIService")
    @Autowired
    IChatAIService aIService;

    @Qualifier("assemblyAITranscribeService")
    @Autowired
    TranscriptionServices transcriptionServices;

    @Autowired
    TranscriptionRepository transcriptionRepository;

    public String askLlm(String source) {

        Transcription transcription = transcriptionRepository.getTranscriptionBySource(source);

        StringBuilder sb = new StringBuilder();
        if (transcription == null) {
            sb.append("Arquivo de transcrição não encontrado \n");
            sb.append("Realizando transcrição \n");
            TranscriptionRequest transcriptionRequest = new TranscriptionRequest(source);
            TranscriptionResponse transcriptionResponse = transcriptionServices.transcribe(transcriptionRequest);

            transcription = new Transcription(source, transcriptionResponse.getContent());
            transcriptionRepository.save(transcription);
            sb.append("Transcrição realizada\n");
        }

        sb.append("Criando resumo: ").append("\n");
        sb.append("\n\n-----------------------\n\n");
        sb.append("Modelo da Ollama: ").append(aIService.getModel()).append("\n");
        sb.append("Resposta da Ollama: ");
        try {
            sb.append(aIService.askLlm("Rick and Morty", transcription.getContent()));
        }catch (Exception e) {
            sb.append(e.getMessage());
            sb.append("Erro...");
        }

        return sb.toString().replace("\n", "<br>");
    }

    public String askLlmquiz(String source) {
        Transcription transcription = transcriptionRepository.getTranscriptionBySource(source);

        StringBuilder sb = new StringBuilder();
        if (transcription == null) {
            sb.append("Arquivo de transcrição não encontrado \n");
            sb.append("Realizando transcrição \n");
            TranscriptionRequest transcriptionRequest = new TranscriptionRequest(source);
            TranscriptionResponse transcriptionResponse = transcriptionServices.transcribe(transcriptionRequest);

            transcription = new Transcription(source, transcriptionResponse.getContent());
            transcriptionRepository.save(transcription);
            sb.append("Transcrição realizada\n");
        }

        try {
            sb.append(aIService.createQuiz("Rick and Morty", transcription.getContent()));
        }catch (Exception e) {
            sb.append("Erro...");
        }

        return sb.toString().replace("\n", "<br>");
    }
}
