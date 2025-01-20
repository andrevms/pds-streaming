package br.com.pds.streaming.framework.domain.subscription.model.entities;

import br.com.pds.streaming.framework.domain.subscription.model.enums.SubscriptionStatus;
import br.com.pds.streaming.framework.domain.subscription.model.enums.SubscriptionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "subscriptions")
public class Subscription implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String userId;
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
