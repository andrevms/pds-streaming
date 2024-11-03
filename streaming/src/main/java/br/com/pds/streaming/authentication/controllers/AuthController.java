package br.com.pds.streaming.authentication.controllers;

import br.com.pds.streaming.authentication.model.dto.login.LoginRequest;
import br.com.pds.streaming.authentication.model.dto.register.RegisterRequest;
import br.com.pds.streaming.authentication.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
       return authService.authenticateUser(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        return authService.registerUser(registerRequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public String logi2n() {
        return "Greetings from Spring Boot!";
    }
}
