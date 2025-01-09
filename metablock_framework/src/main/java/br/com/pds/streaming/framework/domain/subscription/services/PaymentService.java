package br.com.pds.streaming.framework.domain.subscription.services;


import br.com.pds.streaming.framework.domain.subscription.model.dto.CreditCardDTO;
import org.springframework.stereotype.Service;

@Service
public interface PaymentService {

    boolean processCreditCardPayment(CreditCardDTO creditCardDTO);

}
