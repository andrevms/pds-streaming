package br.com.pds.streaming.media.model.dto;

import br.com.pds.streaming.authentication.model.dto.domain.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;
    private Double stars;
    private Instant timestamp;

    private UserDTO user;
}
