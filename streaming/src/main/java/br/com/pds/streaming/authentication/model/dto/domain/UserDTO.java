package br.com.pds.streaming.authentication.model.dto.domain;

import br.com.pds.streaming.domain.subscription.model.dto.SubscriptionDTO;
import br.com.pds.streaming.media.model.dto.CollectableDTO;
import br.com.pds.streaming.media.model.dto.HistoryDTO;
import br.com.pds.streaming.media.model.dto.RatingDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
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
    private SubscriptionDTO subscription;
    private Set<RatingDTO> ratings;
    private Set<CollectableDTO> watchLaterList;
    private HistoryDTO history;
}