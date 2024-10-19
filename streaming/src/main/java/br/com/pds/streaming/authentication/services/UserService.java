package br.com.pds.streaming.authentication.services;

import br.com.pds.streaming.authentication.models.entities.User;
import br.com.pds.streaming.authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User loadUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
