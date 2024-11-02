package br.com.pds.streaming.media.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TvShowDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private ObjectId id;
    private String title;
    private String description;
    private String thumbnailUrl;
    private String animationUrl;

    private List<SeasonDTO> seasons;

    public TvShowDTO(ObjectId id, String title, String description, String thumbnailUrl, String animationUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.animationUrl = animationUrl;
    }
}