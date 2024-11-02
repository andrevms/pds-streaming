package br.com.pds.streaming.media.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private ObjectId id;
    private String videoUrl;
}
