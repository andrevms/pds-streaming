package br.com.pds.streaming.authentication.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "subscriptions_plans")
public class SubscriptionPlan {

    private @MongoId ObjectId id;
    private String name;
    private Set<Role> roles;

    private double value;

    private boolean isActive;
    private String dateCreated;
    private String dateDeleted;


    public SubscriptionPlan(String name, Set<Role> roles, double value) {
        this.name = name;
        this.roles = roles;
        this.value = value;
        this.isActive = true;
        this.dateCreated = new Date().toString();
        this.dateDeleted = new Date().toString();
    }
}
