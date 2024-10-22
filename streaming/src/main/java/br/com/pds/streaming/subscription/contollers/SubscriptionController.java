package br.com.pds.streaming.subscription.contollers;

import br.com.pds.streaming.subscription.model.dto.CreditCardDTO;
import br.com.pds.streaming.subscription.model.dto.RequestSubscriptionDTO;
import br.com.pds.streaming.subscription.services.SubscriptionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serial;
import java.io.Serializable;

@RestController
@RequestMapping(value = "api/subscriptions")
public class SubscriptionController implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Autowired
    private SubscriptionServices subscriptionServices;

    @PostMapping
    public ResponseEntity<?> subscribe(@RequestBody RequestSubscriptionDTO requestSubscriptionDTO) {
        subscriptionServices.subscribeUser(requestSubscriptionDTO);

        return ResponseEntity.ok().build();
    }
}
