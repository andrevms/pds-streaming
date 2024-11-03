package br.com.pds.streaming.authentication.model.dto.login;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LoginResponse {

    private String username;
    private String jwtToken;
    private List<String> roles;
}
