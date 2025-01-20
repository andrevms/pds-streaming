package br.com.pds.streaming.blockfy.media.model.entities;

import br.com.pds.streaming.framework.media.model.entities.LikeRating;
import br.com.pds.streaming.framework.media.model.entities.Media;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class Audio extends Media {

    private String audioUrl;

    @DBRef
    private List<LikeRating> ratings = new ArrayList<>();
}
