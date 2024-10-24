package br.com.pds.streaming.authentication.models.dto.login;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
