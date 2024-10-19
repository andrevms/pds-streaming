package br.com.pds.streaming.authentication.services;

import br.com.pds.streaming.authentication.models.dto.login.LoginRequest;
import br.com.pds.streaming.authentication.models.dto.login.LoginResponse;
import br.com.pds.streaming.authentication.models.dto.register.RegisterRequest;
import br.com.pds.streaming.authentication.models.entities.Role;
import br.com.pds.streaming.authentication.models.entities.SubscriptionPlan;
import br.com.pds.streaming.authentication.models.entities.User;
import br.com.pds.streaming.authentication.repository.UserRepository;
import br.com.pds.streaming.config.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
        Authentication authentication;

        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (AuthenticationException exception) {

            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        LoginResponse response = new LoginResponse(userDetails.getUsername(), jwtToken, roles);

        return ResponseEntity.ok("teste");
    }

    public ResponseEntity<?> registerUser(RegisterRequest registerRequest) {
        try {

            Role role = new Role("ROLE_ADMIM");
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            SubscriptionPlan plan = new SubscriptionPlan("free", roles, 200.0);

            userRepository.save(new User(registerRequest.getEmail(),
                    registerRequest.getUsername(),
                    registerRequest.getPassword(),
                    registerRequest.getFirstName(),
                    registerRequest.getLastName(),
                    plan
            ));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(registerRequest);
    }
}
