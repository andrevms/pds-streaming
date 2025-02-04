package br.com.pds.streaming.framework.domain.subscription.controllers;

import br.com.pds.streaming.framework.domain.subscription.model.dto.RequestSubscriptionDTO;
import br.com.pds.streaming.framework.domain.subscription.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionServices;

    @PostMapping
    public ResponseEntity<?> subscribe(@RequestBody RequestSubscriptionDTO requestSubscriptionDTO) {
        subscriptionServices.subscribeUser(requestSubscriptionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Subscription created successfully.");
    }
}
