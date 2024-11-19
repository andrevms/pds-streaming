package br.com.pds.streaming.transcription.services.assemblyai;

import br.com.pds.streaming.transcription.services.TranscriptionServices;
import com.assemblyai.api.AssemblyAI;
import com.assemblyai.api.resources.transcripts.types.Transcript;
import com.assemblyai.api.resources.transcripts.types.TranscriptLanguageCode;
import com.assemblyai.api.resources.transcripts.types.TranscriptOptionalParams;
import com.assemblyai.api.resources.transcripts.types.TranscriptStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AssemblyAITranscribeService implements TranscriptionServices {

    @Value("${cloud.aws.cloudfront}")
    private String BASE_URL;
    @Autowired
    private AssemblyAI assemblyAIClient;


    @Override
    public String transcribe(String key) {

        String videoUrl = BASE_URL + key;

        TranscriptOptionalParams params = TranscriptOptionalParams.builder().languageCode(TranscriptLanguageCode.PT).build();
        Transcript transcript = assemblyAIClient.transcripts().transcribe(videoUrl, params);

        if (transcript.getStatus() == TranscriptStatus.ERROR) {
            throw new RuntimeException("Transcript failed with error: " + transcript.getError().get());
        }

        assemblyAIClient.transcripts().waitUntilReady(transcript.getId());
        return transcript.toString();
    }
}