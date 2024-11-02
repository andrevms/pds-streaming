package br.com.pds.streaming.media.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EpisodeDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private ObjectId id;
    private String title;
    private String description;
    private VideoDTO video;

    public EpisodeDTO(ObjectId id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
}
