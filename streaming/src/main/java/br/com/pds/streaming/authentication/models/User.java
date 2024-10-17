package br.com.pds.streaming.authentication.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
@Data
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String email;
    private String username;
    private String password;

    private String firstName;
    private String lastName;

    private boolean isValid;
    private String dataDelete;

}
