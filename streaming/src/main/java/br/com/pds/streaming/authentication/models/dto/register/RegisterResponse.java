package br.com.pds.streaming.authentication.models.dto.register;

import br.com.pds.streaming.subscription.model.enums.SubscriptionStatus;
import br.com.pds.streaming.subscription.model.enums.SubscriptionType;
import lombok.Data;

@Data
public class RegisterResponse {
    private String email;
    private String username;

    private String firstName;
    private String lastName;

    private SubscriptionStatus subscriptionStatus;

}
