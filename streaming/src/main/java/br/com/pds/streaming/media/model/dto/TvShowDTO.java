package br.com.pds.streaming.media.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TvShowDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private String description;
    private String thumbnailUrl;
    private String animationUrl;
    private String ratingsAverage;

    private List<SeasonDTO> seasons = new ArrayList<>();

    private List<RatingDTO> ratings = new ArrayList<>();

    public void setRatingsAverage() {
        this.ratingsAverage = !ratings.isEmpty()
                ? String.format("%.2f", ratings.stream().mapToDouble(RatingDTO::getStars).sum() / ratings.size())
                : "Não há avaliações para esta série.";
    }
}
