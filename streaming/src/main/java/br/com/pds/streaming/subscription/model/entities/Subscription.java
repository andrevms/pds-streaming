package br.com.pds.streaming.subscription.model.entities;

import br.com.pds.streaming.subscription.model.enums.SubscriptionStatus;
import br.com.pds.streaming.subscription.model.enums.SubscriptionType;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.cglib.core.Local;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Document(collection = "subscriptions")
public class Subscription implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @MongoId
    private ObjectId id;
    private SubscriptionType type;
    private SubscriptionStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double price;
    private Set<Role> roles;

    public Subscription(SubscriptionType type, SubscriptionStatus status, LocalDate startDate, LocalDate endDate, Double price, Set<Role> roles) {
        this.type = type;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.roles = roles;
    }

    public Subscription(SubscriptionStatus subscriptionStatus, Set<Role> roles) {
        this.status = subscriptionStatus;
        this.roles = roles;
    }
}
