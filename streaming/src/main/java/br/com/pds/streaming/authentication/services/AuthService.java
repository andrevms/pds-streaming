package br.com.pds.streaming.authentication.services;

import br.com.pds.streaming.authentication.model.dto.login.LoginRequest;
import br.com.pds.streaming.authentication.model.dto.login.LoginResponse;
import br.com.pds.streaming.authentication.model.dto.register.RegisterRequest;
import br.com.pds.streaming.authentication.model.entities.Role;
import br.com.pds.streaming.authentication.model.entities.User;
import br.com.pds.streaming.authentication.model.enums.RoleType;
import br.com.pds.streaming.authentication.repositories.RoleRepository;
import br.com.pds.streaming.authentication.repositories.UserRepository;
import br.com.pds.streaming.config.jwt.JwtUtils;
import br.com.pds.streaming.domain.registration.model.entities.BusinessUser;
import br.com.pds.streaming.domain.registration.repositories.BusinessUserRepository;
import br.com.pds.streaming.exceptions.InvalidRoleException;
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

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private BusinessUserRepository businessUserRepository;

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

    public ResponseEntity<?> registerUser(RegisterRequest registerRequest) throws InvalidRoleException {
        try {
            var roleType = RoleType.valueOf(registerRequest.getRole());


            Role role = roleRepository.findByName(roleType.toString())
                    .orElseGet(() -> {
                        Role newRole = new Role(roleType.toString());
                        return roleRepository.save(newRole);
                    });

            Set<Role> roles = new HashSet<>(List.of(role));

            String encryptedPassword = passwordEncoder.encode(registerRequest.getPassword());

            userRepository.save(new User(registerRequest.getEmail(),
                    registerRequest.getUsername(),
                    encryptedPassword,
                    roles
            ));

            saveBusinessUser(registerRequest);

        }catch (IllegalArgumentException e) {
            throw new InvalidRoleException(e.getMessage());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(registerRequest);
    }

    private void saveBusinessUser(RegisterRequest registerRequest) {

        var businessUser = new BusinessUser();

        businessUser.setUsername(registerRequest.getUsername());
        businessUser.setFirstName(registerRequest.getFirstName());
        businessUser.setLastName(registerRequest.getLastName());

        businessUserRepository.save(businessUser);
    }
}
