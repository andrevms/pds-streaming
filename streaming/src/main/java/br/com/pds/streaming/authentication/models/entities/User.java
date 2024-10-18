package br.com.pds.streaming.authentication.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@Document(collection = "users")
public class User implements UserDetails, Serializable {

    private @MongoId ObjectId id;

    private String email;
    private String username;
    private String password;

    private String firstName;
    private String lastName;

    private SubscriptionPlan subscription;

    private boolean isActive;
    private String dateCreated;
    private String dateDeleted;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return subscription.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getAuthority())).collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive; // Implement as needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive; // Implement as needed
    }

    @Override
    public boolean isEnabled() {
        return isActive; // Implement as needed
    }

    public User(String email, String username, String password, String firstName, String lastName) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isActive = true;
        this.dateCreated = new Date().toString();
        this.dateDeleted = new Date().toString();
    }

    public User(String email, String username, String password, String firstName, String lastName, SubscriptionPlan subscription) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isActive = true;
        this.dateCreated = new Date().toString();
        this.dateDeleted = new Date().toString();
        this.subscription = subscription;
    }
}
