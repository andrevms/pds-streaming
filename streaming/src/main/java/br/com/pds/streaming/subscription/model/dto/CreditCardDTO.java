package br.com.pds.streaming.subscription.model.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CreditCardDTO {

    private String cardHolderName;
    private String cardNumber;
    private String expirationDate;
    private String cvv;
}
