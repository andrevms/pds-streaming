package br.com.pds.streaming.blockfy.media.model.entities;

import br.com.pds.streaming.framework.media.model.entities.Media;
import lombok.Data;

@Data
public abstract class Audio extends Media {
    private String audioUrl;
}
