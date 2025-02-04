package br.com.pds.streaming.blockburst.media.model.entities;

import br.com.pds.streaming.framework.media.model.entities.LikeRating;
import br.com.pds.streaming.framework.media.model.entities.Media;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "tv_shows")
public class TvShow extends Media implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @DBRef
    private List<Season> seasons = new ArrayList<>();

    @DBRef
    private List<LikeRating> ratings = new ArrayList<>();
}
