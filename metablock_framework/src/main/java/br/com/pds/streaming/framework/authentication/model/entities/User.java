package br.com.pds.streaming.framework.authentication.model.entities;

import br.com.pds.streaming.framework.domain.subscription.model.entities.Subscription;
import br.com.pds.streaming.framework.media.model.entities.History;
import br.com.pds.streaming.framework.media.model.entities.Media;
import br.com.pds.streaming.framework.media.model.entities.Rating;
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
import java.util.*;
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
    private Set<Rating> ratings = new HashSet<>();
    @DBRef
    private List<Media> watchLaterList = new ArrayList<>();
    @DBRef
    private History history;

    private boolean isActive;
    private String dateCreated;
    private String dateDeleted;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getAuthority())).collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
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
        this.watchLaterList = new ArrayList<>();
    }
}
