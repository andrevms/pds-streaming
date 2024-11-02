package br.com.pds.streaming.subscription.services;


import br.com.pds.streaming.subscription.model.dto.CreditCardDTO;
import org.springframework.stereotype.Service;

@Service
public interface PaymentServices {

    boolean processCreditCardPayment(CreditCardDTO creditCardDTO);

}
