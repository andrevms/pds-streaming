package br.com.pds.streaming.transcription.model.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@Getter
public class TranscriptionResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String content;

}
