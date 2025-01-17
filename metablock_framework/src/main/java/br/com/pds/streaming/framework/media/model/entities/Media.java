package br.com.pds.streaming.framework.media.model.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public abstract class Media implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String title;
    private String description;
    private String thumbnailUrl;
    private String animationUrl;
    private List<String> categories;
}
