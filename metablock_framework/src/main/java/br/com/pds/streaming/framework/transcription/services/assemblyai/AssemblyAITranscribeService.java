package br.com.pds.streaming.framework.transcription.services.assemblyai;

import br.com.pds.streaming.framework.exceptions.EntityNotFoundException;
import br.com.pds.streaming.framework.exceptions.TranscriptionFailedException;
import br.com.pds.streaming.framework.transcription.model.dto.requests.TranscriptionRequest;
import br.com.pds.streaming.framework.transcription.model.dto.responses.TranscriptionResponse;
import br.com.pds.streaming.framework.transcription.services.TranscriptionService;
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
    public TranscriptionResponse transcribe(TranscriptionRequest transcriptionRequest) throws TranscriptionFailedException {

        String source = transcriptionRequest.getSource();

        TranscriptOptionalParams params = TranscriptOptionalParams.builder().languageCode(TranscriptLanguageCode.PT).build();
        Transcript transcript = assemblyAIClient.transcripts().transcribe(source, params);

        if (transcript.getStatus() == TranscriptStatus.ERROR) {
            throw new TranscriptionFailedException(transcript);
        }

        assemblyAIClient.transcripts().waitUntilReady(transcript.getId());
        return new TranscriptionResponse(transcript.getText().orElseThrow(() -> new EntityNotFoundException("Transcription response text")));
    }
}