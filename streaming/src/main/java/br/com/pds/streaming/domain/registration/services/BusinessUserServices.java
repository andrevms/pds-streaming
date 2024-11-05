package br.com.pds.streaming.domain.registration.services;

import br.com.pds.streaming.domain.registration.model.dto.BusinessUserDTO;
import br.com.pds.streaming.domain.registration.model.entities.BusinessUser;
import br.com.pds.streaming.domain.registration.repositories.BusinessUserRepository;
import br.com.pds.streaming.exceptions.UserNotFoundException;
import br.com.pds.streaming.mapper.modelMapper.MyModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessUserServices {

    @Autowired
    private BusinessUserRepository businessUserRepository;
    @Autowired
    private MyModelMapper mapper;

    public List<BusinessUserDTO> findAll() {
        return mapper.convertList(businessUserRepository.findAll(), BusinessUserDTO.class);
    }

    public BusinessUserDTO findById(String id) throws UserNotFoundException {
        var businessUser = businessUserRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        return mapper.convertValue(businessUser, BusinessUserDTO.class);
    }

    public BusinessUserDTO create(BusinessUserDTO businessUserDTO) {

        var businessUser = mapper.convertValue(businessUserDTO, BusinessUser.class);

        System.out.println(businessUser.getRatings());

        return mapper.convertValue(businessUserRepository.save(businessUser), BusinessUserDTO.class);
    }

    public BusinessUserDTO update(BusinessUserDTO businessUserDTO) throws UserNotFoundException {

        var businessUser = businessUserRepository.findById(String.valueOf(businessUserDTO.getId())).orElseThrow(() -> new UserNotFoundException("User not found"));

        businessUser.setUsername(businessUserDTO.getUsername());
        businessUser.setFirstName(businessUserDTO.getFirstName());
        businessUser.setLastName(businessUserDTO.getLastName());
        businessUser.setRatings(businessUser.getRatings());
        businessUser.setTvShows(businessUser.getTvShows());
        businessUser.setMovies(businessUser.getMovies());

        return mapper.convertValue(businessUserRepository.save(businessUser), BusinessUserDTO.class);
    }


    public void deleteById(String id) throws UserNotFoundException {
        var user = businessUserRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        businessUserRepository.delete(user);
    }
}
