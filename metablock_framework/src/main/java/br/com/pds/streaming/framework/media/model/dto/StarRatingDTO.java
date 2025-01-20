package br.com.pds.streaming.framework.media.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StarRatingDTO extends RatingDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Double starRating;
}
