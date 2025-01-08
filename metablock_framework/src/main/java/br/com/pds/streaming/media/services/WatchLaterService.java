package br.com.pds.streaming.media.services;

import br.com.pds.streaming.authentication.model.dto.domain.UserDTO;
import br.com.pds.streaming.authentication.model.entities.User;
import br.com.pds.streaming.authentication.repositories.UserRepository;
import br.com.pds.streaming.exceptions.EntityNotFoundException;
import br.com.pds.streaming.exceptions.MissingOrInvalidMediaException;
import br.com.pds.streaming.mapper.modelMapper.MyModelMapper;
import br.com.pds.streaming.media.model.dto.MediaDTO;
import br.com.pds.streaming.media.model.entities.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WatchLaterService {

    private final UserRepository userRepository;
    private final MediaService mediaService;
    private final MyModelMapper mapper;

    @Autowired
    public WatchLaterService(UserRepository userRepository, MediaService mediaService, MyModelMapper mapper) {
        this.userRepository = userRepository;
        this.mediaService = mediaService;
        this.mapper = mapper;
    }

    public List<? extends MediaDTO> findByUserId(String userId) throws EntityNotFoundException {
        return mapper.convertList(userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(User.class)).getWatchLaterList(), MediaDTO.class);
    }

    public UserDTO insert(String mediaId, String userId) throws EntityNotFoundException {

        var user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(User.class));

        var media = mapper.convertValue(mediaService.findById(mediaId), Media.class);

        user.getWatchLaterList().add(media);

        userRepository.save(user);

        return mapper.convertValue(user, UserDTO.class);
    }

    public void delete(String mediaId, String userId) throws EntityNotFoundException {

        var user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(User.class));

        var media = mapper.convertValue(mediaService.findById(mediaId), Media.class);

        user.getWatchLaterList().remove(media);

        userRepository.save(user);
    }
}
