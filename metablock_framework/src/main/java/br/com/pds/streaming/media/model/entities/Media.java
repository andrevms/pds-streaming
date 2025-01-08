package br.com.pds.streaming.media.model.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public abstract class Media {

    @Id
    private String id;
    private String title;
    private String description;
    private String category;
    private String thumbnailUrl;
    private String animationUrl;
}
