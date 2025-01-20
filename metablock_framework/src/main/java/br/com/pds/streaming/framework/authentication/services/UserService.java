package br.com.pds.streaming.framework.authentication.services;

import br.com.pds.streaming.framework.authentication.model.dto.domain.UserDTO;
import br.com.pds.streaming.framework.authentication.model.entities.Role;
import br.com.pds.streaming.framework.authentication.model.entities.User;
import br.com.pds.streaming.framework.authentication.model.enums.RoleType;
import br.com.pds.streaming.framework.authentication.repositories.RoleRepository;
import br.com.pds.streaming.framework.authentication.repositories.UserRepository;
import br.com.pds.streaming.framework.domain.subscription.model.entities.Subscription;
import br.com.pds.streaming.framework.domain.subscription.model.enums.SubscriptionStatus;
import br.com.pds.streaming.framework.exceptions.EntityNotFoundException;
import br.com.pds.streaming.framework.mapper.modelMapper.MetablockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    @Qualifier("metablockMapper")
    private MetablockMapper mapper;

    @Override
    public User loadUserByUsername(String username)  {
        var user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public List<UserDTO> findAll() {
        return mapper.convertList(userRepository.findAll(), UserDTO.class);
    }

    public UserDTO findById(String id) {
        var user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(User.class));
        return mapper.convertValue(user, UserDTO.class);
    }

    public UserDTO update(UserDTO userDTO, String id) {

        var user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(User.class));
        var subscription = mapper.convertValue(userDTO.getSubscription(), Subscription.class);

        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setSubscription(subscription);
        user.setRatings(user.getRatings());
        user.setWatchLaterList(user.getWatchLaterList());

        return mapper.convertValue(userRepository.save(user), UserDTO.class);
    }

    public UserDTO updateUserSubscription(String id, Subscription subscription) {

        var user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(User.class));

        user.setSubscription(subscription);

        user = updateUserRole(user);
        return mapper.convertValue(userRepository.save(user), UserDTO.class);
    }

    private User updateUserRole(User user) {

        var subscriptionStatus = user.getSubscription().getStatus();

        if (subscriptionStatus.equals(SubscriptionStatus.ACTIVE)) {

            var role = new Role(RoleType.ROLE_USER_PREMIUM.toString());
            Set<Role> roles = getRoles(role);
            user.setRoles(roles);
            return userRepository.save(user);
        }
        else {

            var role = new Role(RoleType.ROLE_PENDING_USER.toString());
            Set<Role> roles = getRoles(role);
            user.setRoles(roles);
            return userRepository.save(user);
        }
    }

    public void deleteById(String id) {
        userRepository.deleteById(id);
    }

    private Set<Role> getRoles(Role role) {
        var entityRole = roleRepository.findByName(RoleType.valueOf(role.getName()).toString())
                .orElseGet(() -> {
                    Role newRole = new Role(RoleType.valueOf(role.getName()).toString());
                    return roleRepository.save(newRole);
                });

        Set<Role> roles = new HashSet<>(List.of(entityRole));
        return roles;
    }
}
