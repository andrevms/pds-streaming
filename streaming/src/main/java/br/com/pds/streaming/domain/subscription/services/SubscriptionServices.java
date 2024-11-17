package br.com.pds.streaming.domain.subscription.services;

import br.com.pds.streaming.authentication.model.dto.domain.UserDTO;
import br.com.pds.streaming.authentication.model.entities.Role;
import br.com.pds.streaming.authentication.model.entities.User;
import br.com.pds.streaming.authentication.repositories.RoleRepository;
import br.com.pds.streaming.authentication.repositories.UserRepository;
import br.com.pds.streaming.authentication.services.UserService;
import br.com.pds.streaming.domain.subscription.model.dto.RequestSubscriptionDTO;
import br.com.pds.streaming.domain.subscription.model.entities.Subscription;
import br.com.pds.streaming.domain.subscription.model.enums.SubscriptionStatus;
import br.com.pds.streaming.domain.subscription.model.enums.SubscriptionType;
import br.com.pds.streaming.domain.subscription.repositories.SubscriptionRepository;
import br.com.pds.streaming.exceptions.InvalidSubscriptionTypeException;
import br.com.pds.streaming.exceptions.PaymentException;
import br.com.pds.streaming.exceptions.UserNotFoundException;
import br.com.pds.streaming.mapper.modelMapper.MyModelMapper;
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
    private UserService userService;
    @Autowired
    private PaymentServices paymentServices;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private MyModelMapper mapper;

    @Transactional
    public void subscribeUser(RequestSubscriptionDTO requestSubscriptionDTO) throws InvalidSubscriptionTypeException, PaymentException, UserNotFoundException {

        var creditCard = requestSubscriptionDTO.getCreditCardDTO();
        if (!paymentServices.processCreditCardPayment(creditCard)) {
            throw new PaymentException("Payment failed");
        }

        var user = userService.loadUserByUsername(requestSubscriptionDTO.getUsername());

        Subscription subscription;
        try {
            subscription = createSubscription(requestSubscriptionDTO);
        } catch (InvalidSubscriptionTypeException e) {
            throw e;
        }

        subscription.setUserId(user.getId());
        System.out.println("Subscription started: " + subscription.getUserId());
        System.out.println(user.getEmail());
        var entitySubscription = subscriptionRepository.save(subscription);
        System.out.println("Subscription ended: " + subscription.getUserId());

        setUserRole(user);
        user.setSubscription(entitySubscription);
        var userDTO = mapper.convertValue(user, UserDTO.class);
        userService.update(userDTO, userDTO.getId());
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
    }
}
