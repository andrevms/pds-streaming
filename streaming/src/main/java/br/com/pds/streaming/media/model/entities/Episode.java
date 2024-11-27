package br.com.pds.streaming.media.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    private List<String> ratings = new ArrayList<>();
}
