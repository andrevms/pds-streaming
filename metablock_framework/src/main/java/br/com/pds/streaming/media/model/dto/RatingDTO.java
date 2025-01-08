package br.com.pds.streaming.media.model.dto;

import br.com.pds.streaming.authentication.model.dto.domain.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingDTO {

    private String id;
    private Instant timestamp;
    private String comment;

    private UserDTO user;
}
