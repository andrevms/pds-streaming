package br.com.pds.streaming.transcription.services;

import br.com.pds.streaming.transcription.model.dto.requests.TranscriptionRequest;
import br.com.pds.streaming.transcription.model.dto.responses.TranscriptionResponse;

public interface TranscriptionServices {

    TranscriptionResponse transcribe(TranscriptionRequest transcriptionRequest);

}
