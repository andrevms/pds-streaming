package br.com.pds.streaming.framework.authentication.model.dto.register;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String username;
    private String password;

    private String firstName;
    private String lastName;
    private String role;
}
