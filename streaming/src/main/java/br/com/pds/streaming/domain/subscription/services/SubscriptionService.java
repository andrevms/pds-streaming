package br.com.pds.streaming.domain.subscription.services;

import br.com.pds.streaming.authentication.controllers.UserController;
import br.com.pds.streaming.authentication.model.entities.Role;
import br.com.pds.streaming.authentication.model.entities.User;
import br.com.pds.streaming.authentication.model.enums.RoleType;
import br.com.pds.streaming.authentication.repositories.RoleRepository;
import br.com.pds.streaming.authentication.repositories.UserRepository;
import br.com.pds.streaming.authentication.services.UserService;
import br.com.pds.streaming.domain.subscription.model.dto.RequestSubscriptionDTO;
import br.com.pds.streaming.domain.subscription.model.entities.Subscription;
import br.com.pds.streaming.domain.subscription.model.enums.SubscriptionStatus;
import br.com.pds.streaming.domain.subscription.model.enums.SubscriptionType;
import br.com.pds.streaming.domain.subscription.repositories.SubscriptionRepository;
import br.com.pds.streaming.exceptions.EntityNotFoundException;
import br.com.pds.streaming.exceptions.InvalidCreditCardNumberException;
import br.com.pds.streaming.exceptions.InvalidSubscriptionTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    @Lazy
    private UserService userService;
    @Qualifier("stonePaymentServiceImpl")
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public void subscribeUser(RequestSubscriptionDTO requestSubscriptionDTO) throws InvalidSubscriptionTypeException, InvalidCreditCardNumberException, EntityNotFoundException {

        var creditCard = requestSubscriptionDTO.getCreditCardDTO();
        if (!paymentService.processCreditCardPayment(creditCard)) {
            throw new InvalidCreditCardNumberException();
        }

        var user = userService.loadUserByUsername(requestSubscriptionDTO.getUsername());

        Subscription subscription = createSubscription(requestSubscriptionDTO);

        subscription.setUserId(user.getId());
        subscription = subscriptionRepository.save(subscription);

        userService.updateUserSubscription(user.getId(), subscription);
    }

    @Transactional
    public void updateSubscriptionStatus(String subscriptionId, SubscriptionStatus status) throws EntityNotFoundException {

        var subscription = subscriptionRepository.findById(subscriptionId).orElseThrow(() -> new EntityNotFoundException(subscriptionId));

        subscription.setStatus(status);
        subscriptionRepository.save(subscription);
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

    public boolean subscriptionIsExpired(User user) throws EntityNotFoundException {

        if (!user.getSubscription().getEndDate().isBefore(LocalDate.now())) {
            return false;
        }

        var subscriptionId = user.getSubscription().getId();
        updateSubscriptionStatus(subscriptionId, SubscriptionStatus.EXPIRED);

        user = userRepository.findById(user.getId()).orElseThrow(() -> new EntityNotFoundException(User.class));

        var subs = subscriptionRepository.findById(user.getSubscription().getId()).orElseThrow(() -> new EntityNotFoundException(Subscription.class));
        user.setSubscription(subs);
        var role = roleRepository.findByName(RoleType.ROLE_PENDING_USER.toString()).orElseThrow(() -> new EntityNotFoundException(Role.class));
        user.setRoles(new HashSet<>(List.of(role)));

        userRepository.save(user);

        return true;
    }
}