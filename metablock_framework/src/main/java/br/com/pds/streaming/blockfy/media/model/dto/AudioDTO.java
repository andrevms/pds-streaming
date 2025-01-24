package br.com.pds.streaming.blockfy.media.model.dto;

import br.com.pds.streaming.framework.media.model.dto.MediaDTO;
import lombok.Data;

@Data
public abstract class AudioDTO extends MediaDTO {
    private String audioUrl;
}
