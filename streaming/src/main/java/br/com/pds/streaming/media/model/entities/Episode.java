package br.com.pds.streaming.media.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "episodes")
public class Episode implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String title;
    private String description;
    private String videoUrl;
    private String thumbnailUrl;
    private String animationUrl;
    private String seasonId;

    private List<String> categories = new ArrayList<>();
}
