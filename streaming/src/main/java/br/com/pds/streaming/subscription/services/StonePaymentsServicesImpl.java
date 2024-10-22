package br.com.pds.streaming.subscription.services;

import br.com.pds.streaming.subscription.model.dto.CreditCardDTO;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class StonePaymentsServicesImpl implements PaymentServices{


    @Override
    public boolean processCreditCardPayment(CreditCardDTO creditCardDTO) {
        Random random = new Random();
        return random.nextBoolean();
    }
}
