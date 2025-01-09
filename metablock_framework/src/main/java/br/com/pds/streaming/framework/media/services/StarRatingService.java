package br.com.pds.streaming.framework.media.services;

import br.com.pds.streaming.framework.authentication.model.entities.User;
import br.com.pds.streaming.framework.authentication.repositories.UserRepository;
import br.com.pds.streaming.framework.exceptions.DuplicatedRatingException;
import br.com.pds.streaming.framework.exceptions.EntityNotFoundException;
import br.com.pds.streaming.framework.mapper.modelMapper.MetablockMapper;
import br.com.pds.streaming.framework.media.model.entities.Rating;
import br.com.pds.streaming.framework.media.model.entities.StarRating;
import br.com.pds.streaming.framework.media.model.dto.StarRatingDTO;
import br.com.pds.streaming.framework.media.repositories.StarRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class StarRatingService {

    private final StarRatingRepository starRatingRepository;
    private final UserRepository userRepository;
    private final MetablockMapper mapper;

    @Autowired
    public StarRatingService(StarRatingRepository starRatingRepository, UserRepository userRepository, MetablockMapper mapper) {
        this.starRatingRepository = starRatingRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public List<StarRatingDTO> findAll() {
        return mapper.convertList(starRatingRepository.findAll(), StarRatingDTO.class);
    }

    public List<StarRatingDTO> findByUserId(String userId) {
        return mapper.convertList(starRatingRepository.findByUserId(userId), StarRatingDTO.class);
    }

    public StarRatingDTO findById(String id) {
        return mapper.convertValue(starRatingRepository.findById(id), StarRatingDTO.class);
    }

    public StarRatingDTO insert(String userId, String mediaId, StarRatingDTO starRatingDTO) throws DuplicatedRatingException, EntityNotFoundException {

        if (!starRatingRepository.findByUserId(userId).stream().filter(sr -> sr.getMediaId().equals(mediaId)).toList().isEmpty()) {
            throw new DuplicatedRatingException();
        }

        var user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(User.class));

        var rating = mapper.convertValue(starRatingDTO, StarRating.class);
        if (rating.getTimestamp() == null) {
            rating.setTimestamp(Instant.now());
        }
        rating.setMediaId(mediaId);

        var createdRating = starRatingRepository.save(rating);

        user.getRatings().add(createdRating);

        userRepository.save(user);

        return mapper.convertValue(createdRating, StarRatingDTO.class);
    }

    public StarRatingDTO update(StarRatingDTO starRatingDTO, String id) throws EntityNotFoundException {

        var rating = starRatingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Rating.class));

        if (starRatingDTO.getComment() != null) {
            rating.setComment(starRatingDTO.getComment());
        }
        rating.setTimestamp(starRatingDTO.getTimestamp() != null ? starRatingDTO.getTimestamp() : Instant.now());
        rating.setStarRating(starRatingDTO.getStarRating());

        var updatedRating = starRatingRepository.save(rating);

        return mapper.convertValue(updatedRating, StarRatingDTO.class);
    }

    public StarRatingDTO patch(StarRatingDTO starRatingDTO, String id) throws EntityNotFoundException {

        var rating = starRatingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Rating.class));

        if (starRatingDTO.getComment() != null) {
            rating.setComment(starRatingDTO.getComment());
        }

        if (starRatingDTO.getTimestamp() != null) {
            rating.setTimestamp(starRatingDTO.getTimestamp());
        }

        if (starRatingDTO.getStarRating() != null) {
            rating.setStarRating(starRatingDTO.getStarRating());
        }

        var patchedRating = starRatingRepository.save(rating);

        return mapper.convertValue(patchedRating, StarRatingDTO.class);
    }

    public void delete(String id) {
        starRatingRepository.deleteById(id);
    }
}
