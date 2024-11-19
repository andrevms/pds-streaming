package br.com.pds.streaming.transcription.model.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

public class TranscriptionRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String source;

    public TranscriptionRequest() {
    }

    public TranscriptionRequest(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
