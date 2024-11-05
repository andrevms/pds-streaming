package br.com.pds.streaming.domain.subscription.services;

import br.com.pds.streaming.domain.subscription.model.dto.CreditCardDTO;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class StonePaymentServiceImpl implements PaymentServices{

    @Override
    public boolean processCreditCardPayment(CreditCardDTO creditCardDTO) {
        Random random = new Random();
        return random.nextBoolean();
    }
}
