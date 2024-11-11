package br.com.pds.streaming.media.model.entities;

import br.com.pds.streaming.domain.registration.model.entities.BusinessUser;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@Document(collection = "ratings")
public class Rating implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private Double stars;
    private Instant timestamp;
    private String movieId;
    private String tvShowId;

    @DBRef
    private BusinessUser businessUser;

    public Rating(String id, String movieId, Double stars) {
        this.id = id;
        this.stars = stars;
        this.movieId = movieId;
        this.timestamp = Instant.now();
    }

    public Rating(String id, Double stars, String tvShowId) {
        this.id = id;
        this.stars = stars;
        this.tvShowId = tvShowId;
        this.timestamp = Instant.now();
    }

    public Rating(String id, String movieId, Double stars, BusinessUser businessUser) {
        this.id = id;
        this.stars = stars;
        this.movieId = movieId;
        this.timestamp = Instant.now();
        this.businessUser = businessUser;
    }

    public Rating(String id, Double stars, String tvShowId, BusinessUser businessUser) {
        this.id = id;
        this.stars = stars;
        this.tvShowId = tvShowId;
        this.timestamp = Instant.now();
        this.businessUser = businessUser;
    }
}
