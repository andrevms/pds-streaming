package br.com.pds.streaming.media.services;

import br.com.pds.streaming.domain.registration.model.entities.BusinessUser;
import br.com.pds.streaming.domain.registration.repositories.BusinessUserRepository;
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
    private BusinessUserRepository businessUserRepository;
    private MyModelMapper mapper;

    @Autowired
    public RatingService(RatingRepository ratingRepository, MovieRepository movieRepository, TvShowRepository tvShowRepository, BusinessUserRepository businessUserRepository, MyModelMapper mapper) {
        this.ratingRepository = ratingRepository;
        this.movieRepository = movieRepository;
        this.tvShowRepository = tvShowRepository;
        this.businessUserRepository = businessUserRepository;
        this.mapper = mapper;
    }

    public List<RatingDTO> findAll() {

        var ratings = ratingRepository.findAll();

        return mapper.convertList(ratings, RatingDTO.class);
    }

    public List<RatingDTO> findByUserId(String userId) {

        var ratings = ratingRepository.findByBusinessUserId(userId);

        return mapper.convertList(ratings, RatingDTO.class);
    }

    public RatingDTO findById(String id) throws ObjectNotFoundException {

        var rating = ratingRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(Rating.class));

        return mapper.convertValue(rating, RatingDTO.class);
    }

    public RatingDTO insert(String movieId, RatingDTO ratingDTO, String userId) throws ObjectNotFoundException, DuplicatedRatingException {

        if (!ratingRepository.findByBusinessUserId(userId).stream().filter(r -> r.getMovieId() != null).filter(r -> r.getMovieId().equals(movieId)).toList().isEmpty()) {
            throw new DuplicatedRatingException();
        }

        var businessUser = businessUserRepository.findById(userId).orElseThrow(() -> new ObjectNotFoundException(BusinessUser.class));

        var movie = movieRepository.findById(movieId).orElseThrow(() -> new ObjectNotFoundException(Movie.class));

        var rating = mapper.convertValue(ratingDTO, Rating.class);
        rating.setMovieId(movieId);
        adjustRatingSpecifications(rating, businessUser);

        var createdRating = ratingRepository.save(rating);

        movie.getRatings().add(createdRating);

        movieRepository.save(movie);

        return mapper.convertValue(createdRating, RatingDTO.class);
    }

    public RatingDTO insert(RatingDTO ratingDTO, String tvShowId, String userId) throws ObjectNotFoundException, DuplicatedRatingException {

        if (!ratingRepository.findByBusinessUserId(userId).stream().filter(r -> r.getTvShowId() != null).filter(r -> r.getTvShowId().equals(tvShowId)).toList().isEmpty()) {
            throw new DuplicatedRatingException();
        }

        var businessUser = businessUserRepository.findById(userId).orElseThrow(() -> new ObjectNotFoundException(BusinessUser.class));

        var tvShow = tvShowRepository.findById(tvShowId).orElseThrow(() -> new ObjectNotFoundException(TvShow.class));

        var rating = mapper.convertValue(ratingDTO, Rating.class);
        rating.setTvShowId(tvShowId);
        adjustRatingSpecifications(rating, businessUser);

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

    public void delete(String id) {
        ratingRepository.deleteById(id);
    }

    private void adjustRatingSpecifications(Rating rating) {

        if (rating.getTimestamp() == null) {
            rating.setTimestamp(Instant.now());
        }

        rating.setStars(min(5.0, max(1.0, rating.getStars())));
    }

    private void adjustRatingSpecifications(Rating rating, BusinessUser businessUser) {

        rating.setBusinessUser(businessUser);

        adjustRatingSpecifications(rating);
    }
}
