package br.com.pds.streaming.domain.subscription.services;

import br.com.pds.streaming.authentication.model.entities.Role;
import br.com.pds.streaming.authentication.model.entities.User;
import br.com.pds.streaming.authentication.repositories.RoleRepository;
import br.com.pds.streaming.authentication.repositories.UserRepository;
import br.com.pds.streaming.authentication.services.UserService;
import br.com.pds.streaming.domain.registration.repositories.BusinessUserRepository;
import br.com.pds.streaming.domain.subscription.model.dto.RequestSubscriptionDTO;
import br.com.pds.streaming.domain.subscription.model.entities.Subscription;
import br.com.pds.streaming.domain.subscription.model.enums.SubscriptionStatus;
import br.com.pds.streaming.domain.subscription.model.enums.SubscriptionType;
import br.com.pds.streaming.domain.subscription.repositories.SubscriptionRepository;
import br.com.pds.streaming.exceptions.InvalidSubscriptionTypeException;
import br.com.pds.streaming.exceptions.PaymentException;
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
    @Autowired
    private PaymentServices paymentServices;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BusinessUserRepository businessUserRepository;

    @Transactional
    public void subscribeUser(RequestSubscriptionDTO requestSubscriptionDTO) throws InvalidSubscriptionTypeException, PaymentException {

        var creditCard = requestSubscriptionDTO.getCreditCardDTO();
        if (!paymentServices.processCreditCardPayment(creditCard)) {
            throw new PaymentException("Payment failed");
        }

        User user;
        user = getUser(requestSubscriptionDTO.getUsername());


        Subscription subscription;
        try {
            subscription = createSubscription(requestSubscriptionDTO);
        } catch (InvalidSubscriptionTypeException e) {
            throw e;
        }

        setUserRole(user);
        subscriptionRepository.save(subscription);

        var businessUser = businessUserRepository.findByUsername(user.getUsername());
        businessUser.setSubscription(subscription);
        businessUserRepository.save(businessUser);
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

        return new Subscription(
                subscriptionType,
                SubscriptionStatus.ACTIVE,
                startDate,
                endDate,
                price
        );
    }

    private void setUserRole(User user) {
        Role role = roleRepository.findByName("ROLE_USER_PREMIUM")
                .orElseGet(() -> {
                    Role newRole = new Role("ROLE_USER_PREMIUM");
                    return roleRepository.save(newRole);
                });


        user.setRoles(new HashSet<>(List.of(role)));
        userRepository.save(user);
    }
}
