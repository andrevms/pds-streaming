package br.com.pds.streaming.framework.media.services;

import br.com.pds.streaming.framework.authentication.model.dto.domain.UserDTO;
import br.com.pds.streaming.framework.authentication.model.entities.User;
import br.com.pds.streaming.framework.authentication.repositories.UserRepository;
import br.com.pds.streaming.framework.exceptions.EntityNotFoundException;
import br.com.pds.streaming.framework.mapper.modelMapper.MetablockMapper;
import br.com.pds.streaming.framework.media.model.dto.MediaDTO;
import br.com.pds.streaming.framework.media.model.entities.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WatchLaterService {

    private final UserRepository userRepository;
    private final MongoRepository mongoRepository;
    private final MetablockMapper mapper;

    @Autowired
    public WatchLaterService(UserRepository userRepository, @Qualifier("mediaRepository") MongoRepository mongoRepository, @Qualifier("metablockMapper") MetablockMapper mapper) {
        this.userRepository = userRepository;
        this.mongoRepository = mongoRepository;
        this.mapper = mapper;
    }

    public List<? extends MediaDTO> findByUserId(String userId) {
        return mapper.convertList(userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(User.class)).getWatchLaterList(), MediaDTO.class);
    }

    public UserDTO insert(String mediaId, String userId) {

        var user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(User.class));

        var media = mapper.convertValue(mongoRepository.findById(mediaId), Media.class);

        user.getWatchLaterList().add(media);

        userRepository.save(user);

        return mapper.convertValue(user, UserDTO.class);
    }

    public void delete(String mediaId, String userId) {

        var user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(User.class));

        var media = mapper.convertValue(mongoRepository.findById(mediaId), Media.class);

        user.getWatchLaterList().remove(media);

        userRepository.save(user);
    }
}
