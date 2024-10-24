package br.com.pds.streaming.authentication.services;

import br.com.pds.streaming.authentication.models.dto.login.LoginRequest;
import br.com.pds.streaming.authentication.models.dto.login.LoginResponse;
import br.com.pds.streaming.authentication.models.dto.register.RegisterRequest;
import br.com.pds.streaming.authentication.models.dto.register.RegisterResponse;
import br.com.pds.streaming.subscription.model.entities.Role;
import br.com.pds.streaming.authentication.models.entities.User;
import br.com.pds.streaming.authentication.repository.UserRepository;
import br.com.pds.streaming.config.jwt.JwtUtils;
import br.com.pds.streaming.subscription.model.entities.Subscription;
import br.com.pds.streaming.subscription.model.enums.SubscriptionStatus;
import br.com.pds.streaming.subscription.model.enums.SubscriptionType;
import br.com.pds.streaming.subscription.repositories.SubscriptionRepository;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;
    private Logger log = Logger.getLogger(AuthService.class.getName());

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
        Authentication authentication;

        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (AuthenticationException exception) {

            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<Object>(map, HttpStatus.UNAUTHORIZED);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        LoginResponse response = new LoginResponse(userDetails.getUsername(), jwtToken, roles);

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> registerUser(RegisterRequest registerRequest) {
        try {
            Role role = new Role("ROLE_PENDING_USER");
            Set<Role> roles = new HashSet<>(List.of(role));
            Subscription plan = subscriptionRepository.save(new Subscription(SubscriptionStatus.PENDING, roles));

            String encryptedPassword = passwordEncoder.encode(registerRequest.getPassword());

            User user = new User(registerRequest.getEmail(),
                    registerRequest.getUsername(),
                    encryptedPassword,
                    registerRequest.getFirstName(),
                    registerRequest.getLastName(),
                    plan
            );

            user.setSubscriptionPlan(plan);

            var registerResponse = getRegisterResponse(user);

            return ResponseEntity.ok(registerResponse);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private RegisterResponse getRegisterResponse(User user) {
        var registerResponse = new RegisterResponse();

        registerResponse.setEmail(user.getEmail());
        registerResponse.setUsername(user.getUsername());
        registerResponse.setFirstName(user.getFirstName());
        registerResponse.setLastName(user.getLastName());
        registerResponse.setSubscriptionStatus(SubscriptionStatus.PENDING);


        return registerResponse;
    }
}
