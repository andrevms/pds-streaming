package br.com.pds.streaming.media.services;

import br.com.pds.streaming.exceptions.ObjectNotFoundException;
import br.com.pds.streaming.mapper.modelMapper.MyModelMapper;
import br.com.pds.streaming.media.model.dto.RatingDTO;
import br.com.pds.streaming.media.model.entities.Rating;
import br.com.pds.streaming.media.repositories.MovieRepository;
import br.com.pds.streaming.media.repositories.RatingRepository;
import br.com.pds.streaming.media.repositories.TvShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    private RatingRepository ratingRepository;
    private MovieRepository movieRepository;
    private TvShowRepository tvShowRepository;
    private MyModelMapper mapper;

    @Autowired
    public RatingService(RatingRepository ratingRepository, MovieRepository movieRepository, TvShowRepository tvShowRepository, MyModelMapper mapper) {
        this.ratingRepository = ratingRepository;
        this.movieRepository = movieRepository;
        this.tvShowRepository = tvShowRepository;
        this.mapper = mapper;
    }

    public List<RatingDTO> findAll() {

        var ratings = ratingRepository.findAll();

        return mapper.convertList(ratings, RatingDTO.class);
    }

    public RatingDTO findByUserId(String userId) {

        var rating = ratingRepository.findByBusinessUserId(userId);

        return mapper.convertValue(rating, RatingDTO.class);
    }

    public RatingDTO findById(String id) throws ObjectNotFoundException {

        var rating = ratingRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Rating not found."));

        return mapper.convertValue(rating, RatingDTO.class);
    }

    public RatingDTO insert(String movieId, RatingDTO ratingDTO) throws ObjectNotFoundException {

        var movie = movieRepository.findById(movieId).orElseThrow(() -> new ObjectNotFoundException("Movie not found"));

        var createdRating = ratingRepository.save(mapper.convertValue(ratingDTO, Rating.class));

        movie.getRatings().add(createdRating);

        movieRepository.save(movie);

        return mapper.convertValue(createdRating, RatingDTO.class);
    }

    public RatingDTO insert(RatingDTO ratingDTO, String tvShowId) throws ObjectNotFoundException {

        var tvShow = tvShowRepository.findById(tvShowId).orElseThrow(() -> new ObjectNotFoundException("TvShow not found."));

        var createdRating = ratingRepository.save(mapper.convertValue(ratingDTO, Rating.class));

        tvShow.getRatings().add(createdRating);

        tvShowRepository.save(tvShow);

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
