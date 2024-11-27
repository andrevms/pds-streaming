package br.com.pds.streaming.media.services;

import br.com.pds.streaming.authentication.model.dto.domain.UserDTO;
import br.com.pds.streaming.authentication.model.entities.User;
import br.com.pds.streaming.authentication.repositories.UserRepository;
import br.com.pds.streaming.exceptions.EntityNotFoundException;
import br.com.pds.streaming.exceptions.MissingOrInvalidMediaException;
import br.com.pds.streaming.mapper.modelMapper.MyModelMapper;
import br.com.pds.streaming.media.model.dto.MediaDTO;
import br.com.pds.streaming.media.model.entities.Media;
import br.com.pds.streaming.media.model.entities.Movie;
import br.com.pds.streaming.media.model.entities.TvShow;
import br.com.pds.streaming.media.repositories.MovieRepository;
import br.com.pds.streaming.media.repositories.TvShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WatchLaterService {

    private final UserRepository userRepository;
    private final TvShowRepository tvShowRepository;
    private final MovieRepository movieRepository;
    private final MyModelMapper mapper;

    @Autowired
    public WatchLaterService(UserRepository userRepository, TvShowRepository tvShowRepository, MovieRepository movieRepository, MyModelMapper mapper) {
        this.userRepository = userRepository;
        this.tvShowRepository = tvShowRepository;
        this.movieRepository = movieRepository;
        this.mapper = mapper;
    }

    public List<? extends MediaDTO> findByUserId(String userId) throws EntityNotFoundException {
        return mapper.convertList(userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(User.class)).getWatchLaterList(), MediaDTO.class);
    }

    public UserDTO insert(String mediaId, String userId) throws EntityNotFoundException {

        var user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(User.class));

        var media = findMediaById(mediaId, user);

        user.getWatchLaterList().add(media);

        userRepository.save(user);

        return mapper.convertValue(user, UserDTO.class);
    }

    public void delete(String mediaId, String userId) throws EntityNotFoundException {

        var user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(User.class));

        var media = findMediaById(mediaId, user);

        user.getWatchLaterList().remove(media);

        userRepository.save(user);
    }

    private Media findMediaById(String mediaId, User user) {

        Optional<TvShow> tvShowOptional = tvShowRepository.findById(mediaId);
        Optional<Movie> movieOptional = movieRepository.findById(mediaId);

        if (tvShowOptional.isPresent()) {
            return mapper.convertValue(tvShowOptional.get(), TvShow.class);
        } else if (movieOptional.isPresent()) {
            return mapper.convertValue(movieOptional.get(), Movie.class);
        } else {
            throw new MissingOrInvalidMediaException(user);
        }
    }
}
