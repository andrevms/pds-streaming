package br.com.pds.streaming.transcription.controller;

import br.com.pds.streaming.transcription.model.dto.requests.TranscriptionRequest;
import br.com.pds.streaming.transcription.model.dto.responses.TranscriptionResponse;
import br.com.pds.streaming.transcription.services.TranscriptionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/video-transcriptions")
public class TranscriptionController {

    @Qualifier("assemblyAITranscribeService")
    @Autowired
    private TranscriptionServices transcriptionServices;

//    @PostMapping("/start/{key}")
//    public StartTranscriptionJobResult startTranscriptionJob(@PathVariable String key) {
//        return amazonTranscriptionServices.startTranscriptionJob(key);
//    }
//
//    @GetMapping("/status/{jobName}")
//    public String getTranscriptionJobStatus(@PathVariable String jobName) {
//        return amazonTranscriptionServices.getTranscriptionJobUri(jobName);
//    }

    @PostMapping("/transcribe")
    public TranscriptionResponse transcribe(@RequestBody TranscriptionRequest transcriptionRequest) {
        return transcriptionServices.transcribe(transcriptionRequest);
    }
}
