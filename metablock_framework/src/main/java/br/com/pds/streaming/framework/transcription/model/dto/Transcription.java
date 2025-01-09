package br.com.pds.streaming.framework.transcription.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "transcriptions")
public class Transcription implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String transcriptionId;
    private String source;
    private String content;

    public Transcription(String source, String content) {
        this.source = source;
        this.content = content;
    }
}
