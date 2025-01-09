package br.com.pds.streaming.framework.authentication.model.dto.login;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
