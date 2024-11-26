package br.com.pds.streaming.transcription.controllers;

import br.com.pds.streaming.transcription.model.dto.requests.TranscriptionRequest;
import br.com.pds.streaming.transcription.model.dto.responses.TranscriptionResponse;
import br.com.pds.streaming.transcription.services.TranscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = {"/api/videotranscriptions", "/api/video-transcriptions", "/api/video_transcriptions"})
public class TranscriptionController {

    @Qualifier("assemblyAITranscribeService")
    @Autowired
    private TranscriptionService transcriptionService;

    @PostMapping("/transcribe")
    public TranscriptionResponse transcribe(@RequestBody TranscriptionRequest transcriptionRequest) {
        return transcriptionService.transcribe(transcriptionRequest);
    }
}
