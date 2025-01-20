package br.com.pds.streaming.framework.authentication.model.dto.domain;

import br.com.pds.streaming.framework.authentication.model.entities.Role;
import br.com.pds.streaming.framework.domain.subscription.model.dto.SubscriptionDTO;
import br.com.pds.streaming.framework.media.model.dto.HistoryDTO;
import br.com.pds.streaming.framework.media.model.dto.MediaDTO;
import br.com.pds.streaming.framework.media.model.dto.RatingDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private Set<Role> roles;
    private SubscriptionDTO subscription;
    private Set<RatingDTO> ratings;
    private List<? extends MediaDTO> watchLaterList;
    private HistoryDTO history;
}