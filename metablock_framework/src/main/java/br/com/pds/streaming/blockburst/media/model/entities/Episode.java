package br.com.pds.streaming.blockburst.media.model.entities;

import br.com.pds.streaming.framework.media.model.entities.Media;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "episodes")
public class Episode extends Media implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String videoUrl;
    private String seasonId;
}
