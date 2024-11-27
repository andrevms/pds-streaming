package br.com.pds.streaming.ai.services;

import br.com.pds.streaming.exceptions.EntityNotFoundException;
import br.com.pds.streaming.exceptions.TranscriptionFailedException;
import br.com.pds.streaming.transcription.model.dto.Transcription;
import br.com.pds.streaming.transcription.model.dto.requests.TranscriptionRequest;
import br.com.pds.streaming.transcription.model.dto.responses.TranscriptionResponse;
import br.com.pds.streaming.transcription.repositories.TranscriptionRepository;
import br.com.pds.streaming.transcription.services.TranscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class ChatService {
    @Qualifier("ollamaAIService")
    @Autowired
    IChatAIService aIService;

    @Qualifier("assemblyAITranscribeService")
    @Autowired
    TranscriptionService transcriptionService;

    @Autowired
    TranscriptionRepository transcriptionRepository;

    public String askLlm(String subject, String source) throws TranscriptionFailedException, EntityNotFoundException {

        Transcription transcription = transcriptionRepository.getTranscriptionBySource(source);

        StringBuilder sb = new StringBuilder();
        if (transcription == null) {
            sb.append("Arquivo de transcrição não encontrado \n");
            sb.append("Realizando transcrição \n");
            TranscriptionRequest transcriptionRequest = new TranscriptionRequest(source);
            TranscriptionResponse transcriptionResponse = transcriptionService.transcribe(transcriptionRequest);

            transcription = new Transcription(source, transcriptionResponse.getContent());
            transcriptionRepository.save(transcription);
            sb.append("Transcrição realizada\n");
        }

        sb.append("Criando resumo: ").append("\n");
        sb.append("\n\n-----------------------\n\n");
        sb.append("Modelo da Ollama: ").append(aIService.getModel()).append("\n");
        sb.append("Resposta da Ollama: ");
        try {

            String body = "{\"model\": \"llama3.2\",\"messages\": [{\"role\": \"system\",\"content\": \"Você deve agir como uma especialista no assunto "+ subject +" e ira sumarizar o texto recebido.\"}, { \"role\": \"user\", \"content\": \""+transcription.getContent() +"\"}],\"stream\": false}";

            sb.append(askLlmHttpRequest(body));
        }catch (Exception e) {
            sb.append(e.getMessage());
            sb.append("Erro...");
        }

        return sb.toString().replace("\n", "<br>");
    }

    public String askLlmquiz(String subject, String source) throws TranscriptionFailedException, EntityNotFoundException {
        Transcription transcription = transcriptionRepository.getTranscriptionBySource(source);

        StringBuilder sb = new StringBuilder();
        if (transcription == null) {
            sb.append("Arquivo de transcrição não encontrado \n");
            sb.append("Realizando transcrição \n");
            TranscriptionRequest transcriptionRequest = new TranscriptionRequest(source);
            TranscriptionResponse transcriptionResponse = transcriptionService.transcribe(transcriptionRequest);

            transcription = new Transcription(source, transcriptionResponse.getContent());
            transcriptionRepository.save(transcription);
            sb.append("Transcrição realizada\n");
        }

        try {

            String body = "{\"model\": \"llama3.2\",\"messages\": [{\"role\": \"system\",\"content\": \"Você deve agir como uma especialista no assunto "+ subject +" E criar 5 perguntas de multipla escolha sobre o texto e mostrar suas respostas\"}, { \"role\": \"user\", \"content\": \""+transcription.getContent() +"\"}],\"stream\": false}";
            sb.append(askLlmHttpRequest(body));
        }catch (Exception e) {
            sb.append("Erro...");
        }

        return sb.toString().replace("\n", "<br>");
    }

    private String askLlmHttpRequest(String body) {
        try {
            // Define the URL endpoint
            String url = "http://ollama:11434/api/chat";

            // Create a HttpClient
            HttpClient client = HttpClient.newHttpClient();


            // Create the HttpRequest
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            // Send the request and get the response asynchronously
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Output the response status and body
            System.out.println("Response Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());
            return response.body();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            return "Erro...";
        }
    }
}
