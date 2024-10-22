package br.com.pds.streaming.subscription.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RequestSubscriptionDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String username;
    private String SubscriptionType;
    private CreditCardDTO creditCardDTO;
}
