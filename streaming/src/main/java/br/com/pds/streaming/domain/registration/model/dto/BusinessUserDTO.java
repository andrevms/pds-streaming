package br.com.pds.streaming.domain.registration.model.dto;

import br.com.pds.streaming.domain.subscription.model.dto.SubscriptionDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BusinessUserDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private SubscriptionDTO subscription;
}