package br.com.pds.streaming.subscription.model.entities;

import br.com.pds.streaming.authentication.models.entities.Role;
import br.com.pds.streaming.subscription.model.enums.SubscriptionStatus;
import br.com.pds.streaming.subscription.model.enums.SubscriptionType;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
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

    public Subscription(SubscriptionType type, SubscriptionStatus status, LocalDate startDate, LocalDate endDate, Double price) {
        this.type = type;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }

    public Subscription(SubscriptionStatus subscriptionStatus) {
        this.status = subscriptionStatus;
    }
}
