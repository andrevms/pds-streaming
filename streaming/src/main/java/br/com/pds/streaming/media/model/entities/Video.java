package br.com.pds.streaming.media.model.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Document(collection = "videos")
public class Video implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @MongoId
    private String id;
    private String videoUrl;
    private String thumbnailUrl;
    private String animationUrl;

    public Video(String id, String videoUrl, String thumbnailUrl, String animationUrl) {
        this.id = id;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.animationUrl = animationUrl;
    }
}
