package br.com.pds.streaming.blockburst.media.model.dto;

import br.com.pds.streaming.framework.media.model.dto.LikeRatingDTO;
import br.com.pds.streaming.framework.media.model.dto.MediaDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MovieResponse extends MediaDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String videoUrl;
    private List<LikeRatingDTO> ratings = new ArrayList<>();
    private int likes;
    private int dislikes;
}
