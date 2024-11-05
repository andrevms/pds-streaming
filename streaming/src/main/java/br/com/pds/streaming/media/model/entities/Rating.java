package br.com.pds.streaming.media.model.entities;

import br.com.pds.streaming.domain.registration.model.entities.BusinessUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

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

    @MongoId
    private ObjectId id;
    private Double stars;
    private Instant timestamp;

    @DBRef(lazy = true)
    private BusinessUser businessUser;

    public Rating(ObjectId id, Double stars) {
        this.id = id;
        this.stars = stars;
        this.timestamp = Instant.now();
    }

    public Rating(ObjectId id, Double stars, BusinessUser businessUser) {
        this.id = id;
        this.stars = stars;
        this.timestamp = Instant.now();
        this.businessUser = businessUser;
    }
}
