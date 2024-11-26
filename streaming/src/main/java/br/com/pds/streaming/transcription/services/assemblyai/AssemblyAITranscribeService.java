package br.com.pds.streaming.transcription.services.assemblyai;

import br.com.pds.streaming.transcription.model.dto.requests.TranscriptionRequest;
import br.com.pds.streaming.transcription.model.dto.responses.TranscriptionResponse;
import br.com.pds.streaming.transcription.services.TranscriptionService;
import com.assemblyai.api.AssemblyAI;
import com.assemblyai.api.resources.transcripts.types.Transcript;
import com.assemblyai.api.resources.transcripts.types.TranscriptLanguageCode;
import com.assemblyai.api.resources.transcripts.types.TranscriptOptionalParams;
import com.assemblyai.api.resources.transcripts.types.TranscriptStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssemblyAITranscribeService implements TranscriptionService {

    @Autowired
    private AssemblyAI assemblyAIClient;

    @Override
    public TranscriptionResponse transcribe(TranscriptionRequest transcriptionRequest) {

        String source = transcriptionRequest.getSource();

        TranscriptOptionalParams params = TranscriptOptionalParams.builder().languageCode(TranscriptLanguageCode.PT).build();
        Transcript transcript = assemblyAIClient.transcripts().transcribe(source, params);

        if (transcript.getStatus() == TranscriptStatus.ERROR) {
            throw new RuntimeException("Transcript failed with error: " + transcript.getError().get());
        }

        assemblyAIClient.transcripts().waitUntilReady(transcript.getId());
        return new TranscriptionResponse(transcript.getText().get());
    }
}