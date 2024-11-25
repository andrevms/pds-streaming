package br.com.pds.streaming.media.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class HistoryNodeWithBothDTO extends HistoryNodeDTO {
    private EpisodeDTO episode;
    private MovieDTO movie;
}
