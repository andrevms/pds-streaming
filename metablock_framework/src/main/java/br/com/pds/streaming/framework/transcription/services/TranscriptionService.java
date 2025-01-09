package br.com.pds.streaming.framework.transcription.services;

import br.com.pds.streaming.framework.exceptions.EntityNotFoundException;
import br.com.pds.streaming.framework.exceptions.TranscriptionFailedException;
import br.com.pds.streaming.framework.transcription.model.dto.requests.TranscriptionRequest;
import br.com.pds.streaming.framework.transcription.model.dto.responses.TranscriptionResponse;

public interface TranscriptionService {

    TranscriptionResponse transcribe(TranscriptionRequest transcriptionRequest) throws TranscriptionFailedException, EntityNotFoundException;

}
