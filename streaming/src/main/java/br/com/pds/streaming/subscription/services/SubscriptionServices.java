package br.com.pds.streaming.subscription.services;

import br.com.pds.streaming.subscription.model.dto.RequestSubscriptionDTO;
import br.com.pds.streaming.subscription.model.entities.Role;
import br.com.pds.streaming.subscription.model.entities.Subscription;
import br.com.pds.streaming.subscription.model.enums.SubscriptionStatus;
import br.com.pds.streaming.subscription.model.enums.SubscriptionType;
import br.com.pds.streaming.subscription.repositories.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
public class SubscriptionServices {

    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private PaymentServices paymentServices;

    public void subscribeUser(RequestSubscriptionDTO requestSubscriptionDTO) {

        SubscriptionStatus active = SubscriptionStatus.ACTIVE;
        var subscriptionType = SubscriptionType.valueOf(requestSubscriptionDTO.getSubscriptionType());
        var subscriptionStatus = SubscriptionStatus.ACTIVE;
        var startDate = LocalDate.now();
        var endDate = LocalDate.now().plusDays(subscriptionType.getDurationInDays());
        var price = subscriptionType.getPrice();
        var role = new Role("ROLE_USER_PREMIUM");
        var roles = new HashSet<>(List.of(role));


        var subscription = new Subscription(
                subscriptionType
                ,subscriptionStatus,
                startDate,
                endDate,
                price,
                roles);

        subscriptionRepository.save(subscription);
    }

}
