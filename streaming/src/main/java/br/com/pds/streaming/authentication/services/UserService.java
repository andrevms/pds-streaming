package br.com.pds.streaming.authentication.services;

import br.com.pds.streaming.authentication.model.dto.domain.UserDTO;
import br.com.pds.streaming.authentication.model.entities.User;
import br.com.pds.streaming.authentication.repositories.UserRepository;
import br.com.pds.streaming.exceptions.UserNotFoundException;
import br.com.pds.streaming.mapper.modelMapper.MyModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MyModelMapper mapper;

    @Override
    public User loadUserByUsername(String username)  {
        var user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public List<User> findAll() {
        // return mapper.convertList(userRepository.findAll(), UserDTO.class);
        return userRepository.findAll();
    }

    public UserDTO findById(String id) throws UserNotFoundException {
        var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return mapper.convertValue(user, UserDTO.class);
    }

    public UserDTO update(UserDTO userDTO, String id) throws UserNotFoundException {

        var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setRatings(user.getRatings());
        user.setTvShows(user.getTvShows());
        user.setMovies(user.getMovies());

        return mapper.convertValue(userRepository.save(user), UserDTO.class);
    }


    public void deleteById(String id) throws UserNotFoundException {
        var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
    }
}
