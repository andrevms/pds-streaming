package br.com.pds.streaming.subscription.services;

import br.com.pds.streaming.subscription.model.dto.CreditCardDTO;

import java.util.Random;

public class StonePaymentServices implements PaymentServices{
    @Override
    public boolean processCreditCardPayment(CreditCardDTO creditCardDTO) {
        return new Random().nextBoolean();
    }
}
