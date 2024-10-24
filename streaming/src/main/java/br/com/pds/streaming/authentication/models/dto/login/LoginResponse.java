package br.com.pds.streaming.authentication.models.dto.login;

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
