package br.com.pds.streaming.framework.media.model.entities;

import br.com.pds.streaming.framework.authentication.model.entities.User;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.Instant;

@Data
public abstract class Rating {

    @Id
    private String id;
    private Instant timestamp;
    private String mediaId;
    private String comment;

    @DBRef
    private User user;
}
