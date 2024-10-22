package br.com.pds.streaming.subscription.model.enums;

import lombok.Getter;

@Getter
public enum SubscriptionType {

    PREMIUM_MONTHLY(29.99, "Premium Monthly Subscription", 30),
    PREMIUM_ANNUALLY(299.99, "Premium Annual Subscription", 365);

    private final double price;
    private final String description;
    private final int durationInDays;

    SubscriptionType(double price, String description, int durationInDays) {
        this.price = price;
        this.description = description;
        this.durationInDays = durationInDays;
    }
}
