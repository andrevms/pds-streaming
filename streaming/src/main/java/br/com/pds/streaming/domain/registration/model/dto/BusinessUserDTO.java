package br.com.pds.streaming.domain.registration.model.dto;

import br.com.pds.streaming.domain.subscription.model.dto.SubscriptionDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BusinessUserDTO implements Serializable {
    ObjectId id;
    String username;
    String firstName;
    String lastName;
    SubscriptionDTO subscription;
}