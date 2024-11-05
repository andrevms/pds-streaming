package br.com.pds.streaming.domain.registration.model.entities;

import br.com.pds.streaming.domain.subscription.model.entities.Subscription;
import br.com.pds.streaming.media.model.entities.Movie;
import br.com.pds.streaming.media.model.entities.Rating;
import br.com.pds.streaming.media.model.entities.TvShow;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "businessUsers")
public class BusinessUser {

    private @MongoId ObjectId id;

    @Indexed(unique = true)
    private String username;

    private String firstName;
    private String lastName;

    @DBRef
    private Subscription subscription;
    @DBRef
    Set<Rating> ratings = new HashSet<>();
    @DBRef
    Set<TvShow> tvShows = new HashSet<>();
    @DBRef
    Set<Movie> movies = new HashSet<>();

    public BusinessUser(String username, String firstName, String lastName) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
