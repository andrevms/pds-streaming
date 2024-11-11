package br.com.pds.streaming.media.model.entities;

import br.com.pds.streaming.domain.registration.model.entities.BusinessUser;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Document(collection = "ratings")
public class Rating implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private Double stars;
    private Instant timestamp;

    @DBRef
    private BusinessUser businessUser;

    public Rating(String id, Double stars) {
        this.id = id;
        this.stars = stars;
        this.timestamp = Instant.now();
    }

    public Rating(String id, Double stars, BusinessUser businessUser) {
        this.id = id;
        this.stars = stars;
        this.timestamp = Instant.now();
        this.businessUser = businessUser;
    }
}
