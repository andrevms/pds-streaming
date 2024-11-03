package br.com.pds.streaming.media.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "movies")
public class Movie implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @MongoId
    private ObjectId id;
    private String title;
    private String description;
    private String videoUrl;
    private String thumbnailUrl;
    private String animationUrl;
}
