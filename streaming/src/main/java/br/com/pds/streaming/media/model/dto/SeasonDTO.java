package br.com.pds.streaming.media.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SeasonDTO extends MediaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<EpisodeDTO> episodes = new ArrayList<>();
}
