package br.com.pds.streaming.authentication.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "roles")
@EqualsAndHashCode
public class Role implements GrantedAuthority, Serializable {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    private boolean isActive;
    private String dateCreated;
    private String dateDeleted;

    public Role(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
