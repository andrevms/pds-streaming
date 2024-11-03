package br.com.pds.streaming.media.services;

import br.com.pds.streaming.exceptions.ObjectNotFoundException;
import br.com.pds.streaming.mapper.modelMapper.MyModelMapper;
import br.com.pds.streaming.media.model.dto.RatingDTO;
import br.com.pds.streaming.media.model.entities.Rating;
import br.com.pds.streaming.media.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private MyModelMapper mapper;

    public List<RatingDTO> findAll() {

        var ratings = ratingRepository.findAll();

        return mapper.convertList(ratings, RatingDTO.class);
    }

    public RatingDTO findByUserId(String userId) {

        var rating = ratingRepository.findByUserId(userId);

        return mapper.convertValue(rating, RatingDTO.class);
    }

    public RatingDTO findById(String id) throws ObjectNotFoundException {

        var rating = ratingRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Rating not found."));

        return mapper.convertValue(rating, RatingDTO.class);
    }

    public RatingDTO insert(RatingDTO ratingDTO) {

        var createdRating = ratingRepository.save(mapper.convertValue(ratingDTO, Rating.class));

        return mapper.convertValue(createdRating, RatingDTO.class);
    }

    public RatingDTO update(RatingDTO ratingDTO, String id) throws ObjectNotFoundException {

        var rating = ratingRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Rating not found."));

        rating.setStars(ratingDTO.getStars());
        rating.setTimestamp(ratingDTO.getTimestamp());

        var updatedRating = ratingRepository.save(rating);

        return mapper.convertValue(updatedRating, RatingDTO.class);
    }

    public void delete(String id) {
        ratingRepository.deleteById(id);
    }
}
