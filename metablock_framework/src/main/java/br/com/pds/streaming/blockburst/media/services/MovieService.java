package br.com.pds.streaming.blockburst.media.services;

import br.com.pds.streaming.blockburst.repositories.MovieRepository;
import br.com.pds.streaming.framework.exceptions.EntityNotFoundException;
import br.com.pds.streaming.framework.exceptions.InvalidVideoException;
import br.com.pds.streaming.blockburst.mapper.modelMapper.BlockburstMapper;
import br.com.pds.streaming.blockburst.media.model.dto.MovieRequest;
import br.com.pds.streaming.blockburst.media.model.dto.MovieResponse;
import br.com.pds.streaming.blockburst.media.model.entities.Movie;
import br.com.pds.streaming.framework.cloud.services.CloudStorageService;
import br.com.pds.streaming.framework.exceptions.InvalidAnimationException;
import br.com.pds.streaming.framework.exceptions.InvalidThumbnailException;
import br.com.pds.streaming.framework.media.repositories.LikeRatingRepository;
import br.com.pds.streaming.framework.media.util.FileExtensionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final LikeRatingRepository ratingRepository;
    private final BlockburstMapper mapper;
    private final CloudStorageService cloudStorageService;

    @Autowired // Tentar instanciar o MediaService com o BlockburstMapper dinamicamente
    public MovieService(MovieRepository movieRepository, LikeRatingRepository ratingRepository, BlockburstMapper mapper, CloudStorageService cloudStorageService) {
        this.movieRepository = movieRepository;
        this.ratingRepository = ratingRepository;
        this.mapper = mapper;
        this.cloudStorageService = cloudStorageService;
    }

    public List<MovieResponse> findAll() {
        return mapper.convertList(movieRepository.findAll(), MovieResponse.class);
    }

    public MovieResponse findById(String id) {
        var movie = movieRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Movie.class));

        return mapper.convertValue(movie, MovieResponse.class);
    }

    public MovieResponse insert(MovieRequest movieRequest) {

        verifyFilesUrl(movieRequest);

        var movie = mapper.convertValue(movieRequest, Movie.class);

        return mapper.convertValue(movieRepository.save(movie), MovieResponse.class);
    }

    public MovieResponse update(MovieRequest movieRequest, String id) {

        verifyFilesUrl(movieRequest);

        var movie = movieRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Movie.class));

        movie.setTitle(movieRequest.getTitle());
        movie.setDescription(movieRequest.getDescription());
        movie.setVideoUrl(movieRequest.getVideoUrl());
        movie.setThumbnailUrl(movieRequest.getThumbnailUrl());
        movie.setAnimationUrl(movieRequest.getAnimationUrl());
        movie.setCategories(movieRequest.getCategories());

        var updatedMovie = movieRepository.save(movie);

        return mapper.convertValue(updatedMovie, MovieResponse.class);
    }

    public MovieResponse patch(MovieRequest movieRequest, String id) {

        var movie = movieRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Movie.class));

        if (movieRequest.getTitle() != null) movie.setTitle(movieRequest.getTitle());
        if (movieRequest.getDescription() != null) movie.setDescription(movieRequest.getDescription());

        if (movieRequest.getVideoUrl() != null) {

            if (!FileExtensionValidator.validateVideoFileExtension(movieRequest.getVideoUrl())) {
                throw new InvalidVideoException(movieRequest.getVideoUrl());
            }

            movie.setVideoUrl(movieRequest.getVideoUrl());
        }

        if (movieRequest.getThumbnailUrl() != null) {

            if (!FileExtensionValidator.validateThumbnailFileExtension(movieRequest.getThumbnailUrl())) {
                throw new InvalidThumbnailException(movieRequest.getThumbnailUrl());
            }

            movie.setThumbnailUrl(movieRequest.getThumbnailUrl());
        }

        if (movieRequest.getAnimationUrl() != null) {

            if (!FileExtensionValidator.validateAnimationFileExtension(movieRequest.getAnimationUrl())) {
                throw new InvalidAnimationException(movieRequest.getAnimationUrl());
            }

            movie.setAnimationUrl(movieRequest.getAnimationUrl());
        }

        var patchedMovie = movieRepository.save(movie);

        return mapper.convertValue(patchedMovie, MovieResponse.class);
    }

    public void delete(String id) {

        deleteOrphanRatings(id);

        var movie = findById(id);
        var movieVideoUrl = movie.getVideoUrl();
        var movieThumbnailUrl = movie.getThumbnailUrl();
        var movieAnimation = movie.getAnimationUrl();

        cloudStorageService.deleteFile(movieVideoUrl);
        cloudStorageService.deleteFile(movieThumbnailUrl);
        cloudStorageService.deleteFile(movieAnimation);

        movieRepository.deleteById(id);
    }

    private void deleteOrphanRatings(String movieId) {

        var movie = movieRepository.findById(movieId).orElseThrow(() -> new EntityNotFoundException(Movie.class));

        ratingRepository.deleteAll(ratingRepository.findAll().stream().filter(r -> movie.getRatings().contains(r)).toList());
    }

    private void verifyFilesUrl(MovieRequest movieRequest) {

        if (!FileExtensionValidator.validateVideoFileExtension(movieRequest.getVideoUrl())) {
            throw new InvalidVideoException(movieRequest.getVideoUrl());
        }

        if (!FileExtensionValidator.validateThumbnailFileExtension(movieRequest.getThumbnailUrl())) {
            throw new InvalidThumbnailException(movieRequest.getThumbnailUrl());
        }

        if (!FileExtensionValidator.validateAnimationFileExtension(movieRequest.getAnimationUrl())) {
            throw new InvalidAnimationException(movieRequest.getAnimationUrl());
        }
    }
}