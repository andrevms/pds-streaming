package br.com.pds.streaming.blockburst.media.services;

import br.com.pds.streaming.blockburst.media.repositories.MovieRepository;
import br.com.pds.streaming.framework.exceptions.EntityNotFoundException;
import br.com.pds.streaming.blockburst.mapper.modelMapper.BlockburstMapper;
import br.com.pds.streaming.blockburst.media.model.dto.MovieRequest;
import br.com.pds.streaming.blockburst.media.model.dto.MovieResponse;
import br.com.pds.streaming.blockburst.media.model.entities.Movie;
import br.com.pds.streaming.framework.cloud.services.CloudStorageService;
import br.com.pds.streaming.framework.media.repositories.LikeRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.pds.streaming.blockburst.media.util.FileExtensionVerifier.verifyVideoUrl;
import static br.com.pds.streaming.framework.media.util.FileExtensionVerifier.*;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final LikeRatingRepository ratingRepository;
    private final BlockburstMapper mapper;
    private final CloudStorageService cloudStorageService;

    @Autowired
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
            verifyVideoUrl(movieRequest);
            movie.setVideoUrl(movieRequest.getVideoUrl());
        }

        if (movieRequest.getThumbnailUrl() != null) {
            verifyThumbnailUrl(movieRequest);
            movie.setThumbnailUrl(movieRequest.getThumbnailUrl());
        }

        if (movieRequest.getAnimationUrl() != null) {
            verifyAnimationUrl(movieRequest);
            movie.setAnimationUrl(movieRequest.getAnimationUrl());
        }

        if (movieRequest.getCategories() != null) {
            movie.setCategories(movieRequest.getCategories());
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
        verifyVideoUrl(movieRequest);
        verifyThumbnailUrl(movieRequest);
        verifyAnimationUrl(movieRequest);
    }
}