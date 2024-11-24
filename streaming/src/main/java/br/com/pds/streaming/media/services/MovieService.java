package br.com.pds.streaming.media.services;

import br.com.pds.streaming.cloud.services.CloudStorageService;
import br.com.pds.streaming.exceptions.*;
import br.com.pds.streaming.mapper.modelMapper.MyModelMapper;
import br.com.pds.streaming.media.model.dto.MovieDTO;
import br.com.pds.streaming.media.model.entities.Movie;
import br.com.pds.streaming.media.model.entities.Rating;
import br.com.pds.streaming.media.repositories.MovieRepository;
import br.com.pds.streaming.media.repositories.RatingRepository;
import br.com.pds.streaming.media.util.FileExtensionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final RatingRepository ratingRepository;
    private final MyModelMapper mapper;
    private final CloudStorageService cloudStorageService;

    @Autowired
    public MovieService(MovieRepository movieRepository, RatingRepository ratingRepository, MyModelMapper mapper, CloudStorageService cloudStorageService) {
        this.movieRepository = movieRepository;
        this.ratingRepository = ratingRepository;
        this.mapper = mapper;
        this.cloudStorageService = cloudStorageService;
    }

    public List<MovieDTO> findAll() {

        var movies = movieRepository.findAll();

        var moviesDTO = mapper.convertList(movies, MovieDTO.class);

        moviesDTO.forEach(x -> {
            refreshRatingsAverage(x);
        });

        return moviesDTO;
    }

    public MovieDTO findById(String id) throws ObjectNotFoundException {

        var movie = movieRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(Movie.class));

        var movieDTO = mapper.convertValue(movie, MovieDTO.class);

        refreshRatingsAverage(movieDTO);

        return movieDTO;
    }

    public MovieDTO insert(MovieDTO movieDTO) throws InvalidAnimationException, InvalidVideoException, InvalidThumbnailException {

        verifyFilesUrl(movieDTO);

        var createdMovie = movieRepository.save(mapper.convertValue(movieDTO, Movie.class));

        var mappedMovie = mapper.convertValue(createdMovie, MovieDTO.class);

        refreshRatingsAverage(mappedMovie);

        return mappedMovie;
    }

    public MovieDTO update(MovieDTO movieDTO, String id) throws ObjectNotFoundException, InvalidAnimationException, InvalidVideoException, InvalidThumbnailException {

        verifyFilesUrl(movieDTO);

        var movie = movieRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(Movie.class));

        movie.setTitle(movieDTO.getTitle());
        movie.setDescription(movieDTO.getDescription());
        movie.setVideoUrl(movieDTO.getVideoUrl());
        movie.setThumbnailUrl(movieDTO.getThumbnailUrl());
        movie.setAnimationUrl(movieDTO.getAnimationUrl());

        var updatedMovie = movieRepository.save(movie);

        var mappedMovie = mapper.convertValue(updatedMovie, MovieDTO.class);

        refreshRatingsAverage(mappedMovie);

        return mappedMovie;
    }

    public MovieDTO patch(MovieDTO movieDTO, String id) throws ObjectNotFoundException, InvalidVideoException, InvalidThumbnailException, InvalidAnimationException {

        var movie = movieRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(Movie.class));

        if (movieDTO.getTitle() != null) {
            movie.setTitle(movieDTO.getTitle());
        }

        if (movieDTO.getDescription() != null) {
            movie.setDescription(movieDTO.getDescription());
        }

        if (movieDTO.getVideoUrl() != null) {

            if (!FileExtensionValidator.validateVideoFileExtension(movieDTO.getVideoUrl())) {
                throw new InvalidVideoException(movieDTO.getVideoUrl());
            }

            movie.setVideoUrl(movieDTO.getVideoUrl());
        }

        if (movieDTO.getThumbnailUrl() != null) {

            if (!FileExtensionValidator.validateThumbnailFileExtension(movieDTO.getThumbnailUrl())) {
                throw new InvalidThumbnailException(movieDTO.getThumbnailUrl());
            }

            movie.setThumbnailUrl(movieDTO.getThumbnailUrl());
        }

        if (movieDTO.getAnimationUrl() != null) {

            if (!FileExtensionValidator.validateAnimationFileExtension(movieDTO.getAnimationUrl())) {
                throw new InvalidAnimationException(movieDTO.getAnimationUrl());
            }

            movie.setAnimationUrl(movieDTO.getAnimationUrl());
        }

        var patchedMovie = movieRepository.save(movie);

        var mappedMovie = mapper.convertValue(patchedMovie, MovieDTO.class);

        refreshRatingsAverage(mappedMovie);

        return mappedMovie;
    }

    public void delete(String id) throws ObjectNotFoundException, InvalidSourceException {

        deleteOrphanRatings(id);

        var movie = findById(id);
        var movieSource = movie.getVideoUrl();
        var movieThumb = movie.getThumbnailUrl();
        var movieAnimation = movie.getAnimationUrl();

        cloudStorageService.deleteFile(movieSource);
        cloudStorageService.deleteFile(movieThumb);
        cloudStorageService.deleteFile(movieAnimation);

        movieRepository.deleteById(id);
    }

    private void deleteOrphanRatings(String movieId) {

        var ratings = ratingRepository.findByMovieId(movieId);

        ratingRepository.deleteAll(ratings);
    }

    private void refreshRatingsAverage(MovieDTO movieDTO) {

        var movieRatings = ratingRepository.findAll().stream().filter(r -> r.getMovieId() != null).filter(r -> r.getMovieId().equals(movieDTO.getId())).toList();

        movieDTO.setRatingsAverage(!movieRatings.isEmpty() ? String.format("%.1f", movieRatings.stream().mapToDouble(Rating::getStars).sum() / movieRatings.size()) : "Não há avaliações para este filme.");
    }

    private void verifyFilesUrl(MovieDTO movieDTO) throws InvalidVideoException, InvalidThumbnailException, InvalidAnimationException {

        if (!FileExtensionValidator.validateVideoFileExtension(movieDTO.getVideoUrl())) {
            throw new InvalidVideoException(movieDTO.getVideoUrl());
        }

        if (!FileExtensionValidator.validateThumbnailFileExtension(movieDTO.getThumbnailUrl())) {
            throw new InvalidThumbnailException(movieDTO.getThumbnailUrl());
        }

        if (!FileExtensionValidator.validateAnimationFileExtension(movieDTO.getAnimationUrl())) {
            throw new InvalidAnimationException(movieDTO.getAnimationUrl());
        }
    }

    public List<MovieDTO> findMovieByTitle(String title) {
        var movies = movieRepository.findMovieByTitleContainingIgnoreCase(title);

        return mapper.convertList(movies, MovieDTO.class);
    }
}
