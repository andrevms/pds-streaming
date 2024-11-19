package br.com.pds.streaming.media.services;

import br.com.pds.streaming.authentication.model.entities.User;
import br.com.pds.streaming.authentication.repositories.UserRepository;
import br.com.pds.streaming.exceptions.DuplicatedRatingException;
import br.com.pds.streaming.exceptions.ObjectNotFoundException;
import br.com.pds.streaming.mapper.modelMapper.MyModelMapper;
import br.com.pds.streaming.media.model.dto.RatingDTO;
import br.com.pds.streaming.media.model.entities.Movie;
import br.com.pds.streaming.media.model.entities.Rating;
import br.com.pds.streaming.media.model.entities.TvShow;
import br.com.pds.streaming.media.repositories.MovieRepository;
import br.com.pds.streaming.media.repositories.RatingRepository;
import br.com.pds.streaming.media.repositories.TvShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

import static java.lang.Math.min;
import static java.lang.Math.max;

@Service
public class RatingService {

    private RatingRepository ratingRepository;
    private MovieRepository movieRepository;
    private TvShowRepository tvShowRepository;
    private UserRepository userRepository;
    private MyModelMapper mapper;

    @Autowired
    public RatingService(RatingRepository ratingRepository, MovieRepository movieRepository, TvShowRepository tvShowRepository, UserRepository userRepository, MyModelMapper mapper) {
        this.ratingRepository = ratingRepository;
        this.movieRepository = movieRepository;
        this.tvShowRepository = tvShowRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public List<RatingDTO> findAll() {

        var ratings = ratingRepository.findAll();

        return mapper.convertList(ratings, RatingDTO.class);
    }

    public List<RatingDTO> findByUserId(String userId) {

        var ratings = ratingRepository.findByUserId(userId);

        return mapper.convertList(ratings, RatingDTO.class);
    }

    public RatingDTO findById(String id) throws ObjectNotFoundException {

        var rating = ratingRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(Rating.class));

        return mapper.convertValue(rating, RatingDTO.class);
    }

    public RatingDTO insert(String movieId, RatingDTO ratingDTO, String userId) throws ObjectNotFoundException, DuplicatedRatingException {

        if (!ratingRepository.findByUserId(userId).stream().filter(r -> r.getMovieId() != null).filter(r -> r.getMovieId().equals(movieId)).toList().isEmpty()) {
            throw new DuplicatedRatingException();
        }

        var user = userRepository.findById(userId).orElseThrow(() -> new ObjectNotFoundException(User.class));

        var movie = movieRepository.findById(movieId).orElseThrow(() -> new ObjectNotFoundException(Movie.class));

        var rating = mapper.convertValue(ratingDTO, Rating.class);
        rating.setMovieId(movieId);
        adjustRatingSpecifications(rating, user);

        var createdRating = ratingRepository.save(rating);

        movie.getRatings().add(createdRating);

        movieRepository.save(movie);

        return mapper.convertValue(createdRating, RatingDTO.class);
    }

    public RatingDTO insert(RatingDTO ratingDTO, String tvShowId, String userId) throws ObjectNotFoundException, DuplicatedRatingException {

        if (!ratingRepository.findByUserId(userId).stream().filter(r -> r.getTvShowId() != null).filter(r -> r.getTvShowId().equals(tvShowId)).toList().isEmpty()) {
            throw new DuplicatedRatingException();
        }

        var user = userRepository.findById(userId).orElseThrow(() -> new ObjectNotFoundException(User.class));

        var tvShow = tvShowRepository.findById(tvShowId).orElseThrow(() -> new ObjectNotFoundException(TvShow.class));

        var rating = mapper.convertValue(ratingDTO, Rating.class);
        rating.setTvShowId(tvShowId);
        adjustRatingSpecifications(rating, user);

        var createdRating = ratingRepository.save(rating);

        tvShow.getRatings().add(createdRating);

        tvShowRepository.save(tvShow);

        return mapper.convertValue(createdRating, RatingDTO.class);
    }

    public RatingDTO update(RatingDTO ratingDTO, String id) throws ObjectNotFoundException {

        var rating = ratingRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(Rating.class));

        rating.setStars(ratingDTO.getStars());
        rating.setTimestamp(ratingDTO.getTimestamp());
        adjustRatingSpecifications(rating);

        var updatedRating = ratingRepository.save(rating);

        return mapper.convertValue(updatedRating, RatingDTO.class);
    }

    public RatingDTO patch(RatingDTO ratingDTO, String id) throws ObjectNotFoundException {

        var rating = ratingRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(Rating.class));

        if (ratingDTO.getStars() != null) {
            rating.setStars(ratingDTO.getStars());
        }

        if (ratingDTO.getTimestamp() != null) {
            rating.setTimestamp(ratingDTO.getTimestamp());
        }

        adjustRatingSpecifications(rating);

        var patchedRating = ratingRepository.save(rating);

        return mapper.convertValue(patchedRating, RatingDTO.class);
    }

    public void delete(String id) {
        ratingRepository.deleteById(id);
    }

    private void adjustRatingSpecifications(Rating rating) {

        if (rating.getTimestamp() == null) {
            rating.setTimestamp(Instant.now());
        }

        rating.setStars(min(5.0, max(1.0, rating.getStars())));
    }

    private void adjustRatingSpecifications(Rating rating, User user) {

        rating.setUser(user);

        adjustRatingSpecifications(rating);
    }
}
