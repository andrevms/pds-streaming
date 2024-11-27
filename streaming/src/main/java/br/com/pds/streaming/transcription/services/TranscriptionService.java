package br.com.pds.streaming.transcription.services;

import br.com.pds.streaming.exceptions.EntityNotFoundException;
import br.com.pds.streaming.exceptions.TranscriptionFailedException;
import br.com.pds.streaming.transcription.model.dto.requests.TranscriptionRequest;
import br.com.pds.streaming.transcription.model.dto.responses.TranscriptionResponse;

public interface TranscriptionService {

    TranscriptionResponse transcribe(TranscriptionRequest transcriptionRequest) throws TranscriptionFailedException, EntityNotFoundException;

}
