package br.com.pds.streaming.framework.authentication.services;

import br.com.pds.streaming.framework.authentication.model.dto.login.LoginRequest;
import br.com.pds.streaming.framework.authentication.model.dto.login.LoginResponse;
import br.com.pds.streaming.framework.authentication.model.dto.register.RegisterRequest;
import br.com.pds.streaming.framework.authentication.model.dto.register.RegisterResponse;
import br.com.pds.streaming.framework.authentication.model.entities.Role;
import br.com.pds.streaming.framework.authentication.model.entities.User;
import br.com.pds.streaming.framework.authentication.model.enums.RoleType;
import br.com.pds.streaming.framework.authentication.repositories.RoleRepository;
import br.com.pds.streaming.framework.authentication.repositories.UserRepository;
import br.com.pds.streaming.framework.config.jwt.JwtUtils;
import br.com.pds.streaming.framework.exceptions.InvalidRoleException;
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
import java.util.stream.Collectors;

@Service
public class AuthService {

    private JwtUtils jwtUtils;
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(JwtUtils jwtUtils, AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

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
            User user = mountUser(registerRequest);

            userRepository.save(user);

            List<String> roles = user.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            RegisterResponse registerResponse = new RegisterResponse(user.getUsername(), roles);
            return ResponseEntity.ok(registerResponse);

        } catch (IllegalArgumentException e) {
            throw new InvalidRoleException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private User mountUser(RegisterRequest registerRequest) {

        String email = registerRequest.getEmail();
        String username = registerRequest.getUsername();
        String encryptedPassword = passwordEncoder.encode(registerRequest.getPassword());
        String firstName = registerRequest.getFirstName();
        String lastName = registerRequest.getLastName();

        var roleType = RoleType.valueOf(registerRequest.getRole());

        Role role = roleRepository.findByName(roleType.toString())
                .orElseGet(() -> {
                    Role newRole = new Role(roleType.toString());
                    return roleRepository.save(newRole);
                });

        var roles = new HashSet<>(List.of(role));

        return new User(email, username, encryptedPassword, firstName, lastName, roles, null);
    }
}
