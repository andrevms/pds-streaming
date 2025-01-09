package br.com.pds.streaming.framework.media.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryNodeDTO<T extends MediaDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private T media;
    private long currentTime; // in milliseconds
}
