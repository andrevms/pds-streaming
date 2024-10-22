package br.com.pds.streaming.subscription.services;

import br.com.pds.streaming.authentication.models.entities.User;
import br.com.pds.streaming.authentication.repository.UserRepository;
import br.com.pds.streaming.authentication.services.UserService;
import br.com.pds.streaming.exceptions.InvalidSubscriptionTypeException;
import br.com.pds.streaming.subscription.model.dto.RequestSubscriptionDTO;
import br.com.pds.streaming.subscription.model.entities.Role;
import br.com.pds.streaming.subscription.model.entities.Subscription;
import br.com.pds.streaming.subscription.model.enums.SubscriptionStatus;
import br.com.pds.streaming.subscription.model.enums.SubscriptionType;
import br.com.pds.streaming.subscription.repositories.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
public class SubscriptionServices {

    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Transactional
    public void subscribeUser(RequestSubscriptionDTO requestSubscriptionDTO) throws InvalidSubscriptionTypeException {
        User user;
        user = getUser(requestSubscriptionDTO.getUsername());

        Subscription subscription;
        try {
            subscription = createSubscription(requestSubscriptionDTO);
        } catch (InvalidSubscriptionTypeException e) {
            throw e;
        }

        user.setSubscriptionPlan(subscription);

        userRepository.save(user);
        subscriptionRepository.save(subscription);
    }

    private User getUser(String username) {
        return userService.loadUserByUsername(username);
    }

    private Subscription createSubscription(RequestSubscriptionDTO requestSubscriptionDTO) throws InvalidSubscriptionTypeException {
        SubscriptionType subscriptionType;
        try {
            subscriptionType = SubscriptionType.valueOf(requestSubscriptionDTO.getSubscriptionType());
        } catch (IllegalArgumentException e) {
            throw new InvalidSubscriptionTypeException(requestSubscriptionDTO.getSubscriptionType());
        }

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(subscriptionType.getDurationInDays());
        double price = subscriptionType.getPrice();
        var roles = new HashSet<>(List.of(new Role("ROLE_USER_PREMIUM")));

        return new Subscription(
                subscriptionType,
                SubscriptionStatus.ACTIVE,
                startDate,
                endDate,
                price,
                roles
        );
    }
}
