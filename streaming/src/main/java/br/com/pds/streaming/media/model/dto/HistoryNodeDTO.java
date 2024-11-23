package br.com.pds.streaming.media.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryNodeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private WatchableDTO watchableDTO;
    private long currentTime; // in milliseconds
}
