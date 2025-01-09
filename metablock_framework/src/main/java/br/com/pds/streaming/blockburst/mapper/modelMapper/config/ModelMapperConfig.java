package br.com.pds.streaming.blockburst.mapper.modelMapper.config;

import br.com.pds.streaming.blockburst.media.model.dto.*;
import br.com.pds.streaming.blockburst.media.model.entities.Episode;
import br.com.pds.streaming.blockburst.media.model.entities.Movie;
import br.com.pds.streaming.blockburst.media.model.entities.Season;
import br.com.pds.streaming.blockburst.media.model.entities.TvShow;
import br.com.pds.streaming.framework.media.model.dto.LikeRatingDTO;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new br.com.pds.streaming.framework.mapper.modelMapper.config.ModelMapperConfig().modelMapper();

        final Converter<Movie, MovieResponse> movieConverter = ctx -> {

            var source = ctx.getSource();
            var destination = ctx.getDestination();

            destination.setId(source.getId());
            destination.setTitle(source.getTitle());
            destination.setDescription(source.getDescription());
            destination.setThumbnailUrl(source.getThumbnailUrl());
            destination.setAnimationUrl(source.getAnimationUrl());
            destination.setCategories(source.getCategories());

            destination.setVideoUrl(source.getVideoUrl());
            destination.setRatings(source.getRatings().stream().map(rating -> modelMapper.map(rating, LikeRatingDTO.class)).toList());
            destination.setLikes(source.getRatings().stream().filter(r -> r.isLiked()).toList().size());
            destination.setDislikes(source.getRatings().stream().filter(r -> !r.isLiked()).toList().size());

            return destination;
        };

        final Converter<TvShow, TvShowResponse> tvShowConverter = ctx -> {

            var source = ctx.getSource();
            var destination = ctx.getDestination();

            destination.setId(source.getId());
            destination.setTitle(source.getTitle());
            destination.setDescription(source.getDescription());
            destination.setThumbnailUrl(source.getThumbnailUrl());
            destination.setAnimationUrl(source.getAnimationUrl());
            destination.setCategories(source.getCategories());

            destination.setSeasons(source.getSeasons().stream().map(season -> modelMapper.map(season, SeasonDTO.class)).toList());
            destination.setRatings(source.getRatings().stream().map(rating -> modelMapper.map(rating, LikeRatingDTO.class)).toList());
            destination.setLikes(source.getRatings().stream().filter(r -> r.isLiked()).toList().size());
            destination.setDislikes(source.getRatings().stream().filter(r -> !r.isLiked()).toList().size());

            return destination;
        };

        modelMapper.createTypeMap(Episode.class, EpisodeDTO.class).addMapping(Episode::getId, EpisodeDTO::setId);
        modelMapper.createTypeMap(EpisodeDTO.class, Episode.class).addMapping(EpisodeDTO::getId, Episode::setId);

        modelMapper.createTypeMap(Movie.class, MovieResponse.class).setConverter(movieConverter);
        modelMapper.createTypeMap(MovieRequest.class, Movie.class).addMapping(MovieRequest::getId, Movie::setId);

        modelMapper.createTypeMap(Season.class, SeasonDTO.class).addMapping(Season::getId, SeasonDTO::setId);
        modelMapper.createTypeMap(SeasonDTO.class, Season.class).addMapping(SeasonDTO::getId, Season::setId);

        modelMapper.createTypeMap(TvShow.class, TvShowResponse.class).setConverter(tvShowConverter);
        modelMapper.createTypeMap(TvShowRequest.class, TvShow.class).addMapping(TvShowRequest::getId, TvShow::setId);

        return modelMapper;
    }
}
