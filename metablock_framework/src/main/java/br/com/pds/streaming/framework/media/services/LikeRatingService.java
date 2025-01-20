package br.com.pds.streaming.framework.media.services;

import br.com.pds.streaming.framework.authentication.model.entities.User;
import br.com.pds.streaming.framework.authentication.repositories.UserRepository;
import br.com.pds.streaming.framework.exceptions.DuplicatedRatingException;
import br.com.pds.streaming.framework.exceptions.EntityNotFoundException;
import br.com.pds.streaming.framework.mapper.modelMapper.MetablockMapper;
import br.com.pds.streaming.framework.media.model.entities.LikeRating;
import br.com.pds.streaming.framework.media.model.entities.Rating;
import br.com.pds.streaming.framework.media.model.dto.LikeRatingDTO;
import br.com.pds.streaming.framework.media.repositories.LikeRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class LikeRatingService {

    private final LikeRatingRepository likeRatingRepository;
    private final UserRepository userRepository;
    private final MetablockMapper mapper;

    @Autowired
    public LikeRatingService(LikeRatingRepository likeRatingRepository, UserRepository userRepository, @Qualifier("metablockMapper") MetablockMapper mapper) {
        this.likeRatingRepository = likeRatingRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public List<LikeRatingDTO> findAll() {
        return mapper.convertList(likeRatingRepository.findAll(), LikeRatingDTO.class);
    }

    public List<LikeRatingDTO> findByUserId(String userId) {
        return mapper.convertList(likeRatingRepository.findByUserId(userId), LikeRatingDTO.class);
    }

    public LikeRatingDTO findById(String id) {
        return mapper.convertValue(likeRatingRepository.findById(id), LikeRatingDTO.class);
    }

    public LikeRatingDTO insert(String userId, String mediaId, LikeRatingDTO likeRatingDTO) {

        if (!likeRatingRepository.findByUserId(userId).stream().filter(lr -> lr.getMediaId().equals(mediaId)).toList().isEmpty()) {
            throw new DuplicatedRatingException();
        }

        var user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(User.class));

        var rating = mapper.convertValue(likeRatingDTO, LikeRating.class);
        if (rating.getTimestamp() == null) {
            rating.setTimestamp(Instant.now());
        }
        rating.setMediaId(mediaId);

        var createdRating = likeRatingRepository.save(rating);

        user.getRatings().add(createdRating);

        userRepository.save(user);

        return mapper.convertValue(createdRating, LikeRatingDTO.class);
    }

    public LikeRatingDTO update(LikeRatingDTO likeRatingDTO, String id) {

        var rating = likeRatingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Rating.class));

        if (likeRatingDTO.getComment() != null) {
            rating.setComment(likeRatingDTO.getComment());
        }
        rating.setTimestamp(likeRatingDTO.getTimestamp() != null ? likeRatingDTO.getTimestamp() : Instant.now());
        rating.setLiked(likeRatingDTO.isLiked());

        var updatedRating = likeRatingRepository.save(rating);

        return mapper.convertValue(updatedRating, LikeRatingDTO.class);
    }

    public LikeRatingDTO patch(LikeRatingDTO likeRatingDTO, String id) {

        var rating = likeRatingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Rating.class));

        if (likeRatingDTO.getComment() != null) {
            rating.setComment(likeRatingDTO.getComment());
        }

        if (likeRatingDTO.getTimestamp() != null) {
            rating.setTimestamp(likeRatingDTO.getTimestamp());
        }

        rating.setLiked(likeRatingDTO.isLiked());

        var patchedRating = likeRatingRepository.save(rating);

        return mapper.convertValue(patchedRating, LikeRatingDTO.class);
    }

    public void deleteById(String id) {
        likeRatingRepository.deleteById(id);
    }
}
