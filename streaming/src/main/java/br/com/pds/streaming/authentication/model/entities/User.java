package br.com.pds.streaming.authentication.model.entities;

import br.com.pds.streaming.domain.subscription.model.entities.Subscription;
import br.com.pds.streaming.media.model.entities.Collectable;
import br.com.pds.streaming.media.model.entities.Rating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
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
@NoArgsConstructor
@Document(collection = "users")
public class User implements UserDetails, Serializable {

    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    private String firstName;
    private String lastName;

    @Indexed(unique = true)
    private String username;
    private String password;

    @DBRef
    private Set<Role> roles = new HashSet<>();
    @DBRef
    private Subscription subscription;
    @DBRef
    Set<Rating> ratings = new HashSet<>();
    @DBRef
    Set<Collectable> watchLaterList = new HashSet<>();

    private boolean isActive;
    private String dateCreated;
    private String dateDeleted;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getAuthority())).collect(Collectors.toList());
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

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.isActive = true;
        this.dateCreated = new Date().toString();
        this.dateDeleted = new Date().toString();
    }

    public User(String email, String username, String password, String firstName, String lastName, Set<Role> roles, Subscription subscription) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isActive = true;
        this.dateCreated = new Date().toString();
        this.dateDeleted = new Date().toString();
        this.roles = roles;
        this.subscription = subscription;
        this.ratings = new HashSet<>();
        this.watchLaterList = new HashSet<>();
    }
}
