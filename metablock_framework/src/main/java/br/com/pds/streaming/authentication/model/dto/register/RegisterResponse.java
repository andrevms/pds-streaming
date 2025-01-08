package br.com.pds.streaming.authentication.model.dto.register;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class RegisterResponse {
    private String username;
    private List<String> roles;
}
