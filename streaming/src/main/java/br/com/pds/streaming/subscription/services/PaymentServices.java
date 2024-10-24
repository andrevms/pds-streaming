package br.com.pds.streaming.subscription.services;


import br.com.pds.streaming.subscription.model.dto.CreditCardDTO;

public interface PaymentServices {

    boolean processCreditCardPayment(CreditCardDTO creditCardDTO);

}
