package br.com.pds.streaming.framework.media.model.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public abstract class Media {

    @Id
    private String id;
    private String title;
    private String description;
    private String thumbnailUrl;
    private String animationUrl;
    private List<String> categories;
}
