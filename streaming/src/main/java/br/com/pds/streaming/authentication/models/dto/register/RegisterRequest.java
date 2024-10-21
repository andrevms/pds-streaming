package br.com.pds.streaming.authentication.models.dto.register;

import br.com.pds.streaming.authentication.models.entities.SubscriptionPlan;
import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String username;
    private String password;

    private String firstName;
    private String lastName;

    private String subscriptionName;
}
