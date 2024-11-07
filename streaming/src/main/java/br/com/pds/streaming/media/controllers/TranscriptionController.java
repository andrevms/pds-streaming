package br.com.pds.streaming.media.controllers;

import br.com.pds.streaming.cloud.amazon.AmazonTranscriptionServices;
import com.amazonaws.services.transcribe.model.StartTranscriptionJobResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/video-transcriptions")
public class TranscriptionController {

    @Autowired
    private AmazonTranscriptionServices amazonTranscriptionServices;

    @PostMapping("/start/{key}")
    public StartTranscriptionJobResult startTranscriptionJob(@PathVariable String key) {
        return amazonTranscriptionServices.startTranscriptionJob(key);
    }

    @GetMapping("/status/{jobName}")
    public String getTranscriptionJobStatus(@PathVariable String jobName) {
        return amazonTranscriptionServices.getTranscriptionJobUri(jobName);
    }

    @GetMapping("/transcript/{transcriptUri}")
    public String getTranscriptionText(@PathVariable String transcriptUri) {
        return amazonTranscriptionServices.getTranscriptionText(transcriptUri);
    }
}
