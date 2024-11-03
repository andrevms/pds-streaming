package br.com.pds.streaming.domain.subscription.services;


import br.com.pds.streaming.domain.subscription.model.dto.CreditCardDTO;
import org.springframework.stereotype.Service;

@Service
public interface PaymentServices {

    boolean processCreditCardPayment(CreditCardDTO creditCardDTO);

}
