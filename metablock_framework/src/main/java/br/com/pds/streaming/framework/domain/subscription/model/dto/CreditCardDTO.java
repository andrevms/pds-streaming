package br.com.pds.streaming.framework.domain.subscription.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreditCardDTO {

    private String cardHolderName;
    private String cardNumber;
    private String expirationDate;
    private String cvv;
}
