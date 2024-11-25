package br.com.pds.streaming.media.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;
    private List<HistoryNodeDTO> nodes = new ArrayList<>();
}
