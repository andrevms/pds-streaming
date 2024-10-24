package br.com.pds.streaming.media.model.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "tv_shows")
public class TvShow implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @MongoId
    private ObjectId id;
    private String title;
    private String description;
    private String thumbnailUrl;
    private String animationUrl;

    @DBRef(lazy = true)
    private List<Season> seasons = new ArrayList<>();

    public TvShow(ObjectId id, String title, String description, String thumbnailUrl, String animationUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.animationUrl = animationUrl;
    }

    public TvShow(ObjectId id, String title, String description, String thumbnailUrl, String animationUrl, List<Season> seasons) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.animationUrl = animationUrl;
        this.seasons = seasons;
    }
}
