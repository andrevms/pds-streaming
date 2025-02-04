package br.com.pds.streaming.framework.domain.subscription.services;

import br.com.pds.streaming.framework.domain.subscription.model.dto.CreditCardDTO;
import org.springframework.stereotype.Service;

@Service
public class StonePaymentServiceImpl implements PaymentService {

    @Override
    public boolean processCreditCardPayment(CreditCardDTO creditCardDTO) {
        return isValidCreditCardNumberValid(Long.parseLong(creditCardDTO.getCardNumber()));
    }

    public boolean isValidCreditCardNumberValid(long number) {
        return (getSize(number) >= 13 && getSize(number) <= 16) && (prefixMatched(number, 4) || prefixMatched(number, 5) || prefixMatched(number, 37) || prefixMatched(number, 6)) && ((sumOfDoubleEvenPlace(number) + sumOfOddPlace(number)) % 10 == 0);
    }

    public int sumOfDoubleEvenPlace(long number) {
        int sum = 0;
        String num = number + "";
        for (int i = getSize(number) - 2; i >= 0; i -= 2)
            sum += getDigit(Integer.parseInt(num.charAt(i) + "") * 2);

        return sum;
    }

    public int getDigit(int number) {
        if (number < 9)
            return number;
        return number / 10 + number % 10;
    }

    public int sumOfOddPlace(long number) {
        int sum = 0;
        String num = number + "";
        for (int i = getSize(number) - 1; i >= 0; i -= 2)
            sum += Integer.parseInt(num.charAt(i) + "");
        return sum;
    }

    public boolean prefixMatched(long number, int d) {
        return getPrefix(number, getSize(d)) == d;
    }

    public int getSize(long d) {
        String num = d + "";
        return num.length();
    }

    public long getPrefix(long number, int k) {
        if (getSize(number) > k) {
            String num = number + "";
            return Long.parseLong(num.substring(0, k));
        }
        return number;
    }
}
