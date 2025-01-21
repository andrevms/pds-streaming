package br.com.pds.streaming.blockfy.media.model.entities;

import br.com.pds.streaming.framework.authentication.model.entities.User;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = true)
@Document(collection = "music")
public class Music extends Audio implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String musicGenre;
    private List<String> artists = new ArrayList<>();
    private List<User> users = new ArrayList<>();
}
