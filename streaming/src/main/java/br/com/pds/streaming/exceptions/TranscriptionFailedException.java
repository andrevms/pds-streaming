package br.com.pds.streaming.exceptions;

import com.assemblyai.api.resources.transcripts.types.Transcript;

public class TranscriptionFailedException extends Exception {

    public TranscriptionFailedException(Transcript transcript) {
        super("Transcript failed with error: " + transcript.getError().orElse("Unknown error"));
    }
}
