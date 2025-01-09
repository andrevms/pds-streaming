package br.com.pds.streaming.framework.domain.subscription.model.dto;

import br.com.pds.streaming.framework.domain.subscription.model.enums.SubscriptionStatus;
import br.com.pds.streaming.framework.domain.subscription.model.enums.SubscriptionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;
    private SubscriptionType type;
    private SubscriptionStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double price;

}
