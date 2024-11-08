package br.com.pds.streaming.transcription.controller;

import br.com.pds.streaming.transcription.services.TranscriptionServices;
import br.com.pds.streaming.transcription.services.amazon.AmazonTranscribeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/video-transcriptions")
public class TranscriptionController {

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

    @GetMapping("/transcript/{key}")
    public String getTranscriptionText(@PathVariable String key) {
        return transcriptionServices.transcribe(key);
    }
}
