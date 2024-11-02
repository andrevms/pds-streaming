package br.com.pds.streaming.subscription.controllers;

import br.com.pds.streaming.exceptions.InvalidSubscriptionTypeException;
import br.com.pds.streaming.exceptions.PaymentException;
import br.com.pds.streaming.exceptions.UserNotFoundException;
import br.com.pds.streaming.exceptions.response.ResponseError;
import br.com.pds.streaming.subscription.model.dto.RequestSubscriptionDTO;
import br.com.pds.streaming.subscription.services.SubscriptionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "api/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionServices subscriptionServices;

    // TODO("Organizar os tratamentos de excecoes")
    @PostMapping
    public ResponseEntity<?> subscribe(@RequestBody RequestSubscriptionDTO requestSubscriptionDTO) {
        try {
            subscriptionServices.subscribeUser(requestSubscriptionDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Subscription created successfully.");
        } catch (InvalidSubscriptionTypeException e) {
            ResponseError responseError = new ResponseError(
                    LocalDateTime.now(),
                    HttpStatus.BAD_REQUEST.value(),
                    "Invalid Subscription Type",
                    e.getMessage(),
                    "/api/subscriptions"
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseError);
        }catch (PaymentException e) {
            ResponseError responseError = new ResponseError(
                    LocalDateTime.now(),
                    HttpStatus.BAD_REQUEST.value(),
                    "Payment failed",
                    e.getMessage(),
                    "/api/subscriptions"
            );

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseError);
        }
        catch (Exception e) {
            ResponseError responseError = new ResponseError(
                    LocalDateTime.now(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Internal Server Error",
                    e.getMessage(),
                    "/api/subscriptions"
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseError);
        }
    }
}
