package br.com.pds.streaming.blockfy.media.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "music")
public class Music extends Audio implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String musicGenre;
    private List<String> artists = new ArrayList<>();
    private List<String> usersId = new ArrayList<>();
}
