package br.com.pds.streaming.blockfy.media.model.dto;

import br.com.pds.streaming.framework.authentication.model.dto.domain.UserDTO;
import br.com.pds.streaming.framework.authentication.model.entities.User;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Getter
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MusicDTO extends AudioDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String musicGenre;
    private List<String> artists = new ArrayList<>();
    private List<UserDTO> usersDTO = new ArrayList<>();

}
