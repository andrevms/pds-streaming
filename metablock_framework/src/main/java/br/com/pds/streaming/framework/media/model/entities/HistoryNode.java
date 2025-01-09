package br.com.pds.streaming.framework.media.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "history_nodes")
public class HistoryNode implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @DBRef
    private Media media;

    private long currentTime; // in milliseconds
    private String historyId;
}
