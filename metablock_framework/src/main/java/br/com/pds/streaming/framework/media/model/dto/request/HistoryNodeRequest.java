package br.com.pds.streaming.framework.media.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryNodeRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private long currentTime; // in milliseconds
    private String type;

}
