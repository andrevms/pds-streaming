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
@Document(collection = "podcasts")
public class Podcast extends Audio implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String videoUrl;
    private List<String> hosts = new ArrayList<>();
    private List<String> guests = new ArrayList<>();
}
