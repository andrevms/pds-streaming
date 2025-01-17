package br.com.pds.streaming.blockburst.mapper.modelMapper.config;

import br.com.pds.streaming.blockburst.media.model.dto.*;
import br.com.pds.streaming.blockburst.media.model.entities.Episode;
import br.com.pds.streaming.blockburst.media.model.entities.Movie;
import br.com.pds.streaming.blockburst.media.model.entities.Season;
import br.com.pds.streaming.blockburst.media.model.entities.TvShow;
import br.com.pds.streaming.framework.exceptions.MissingOrInvalidMediaException;
import br.com.pds.streaming.framework.media.model.dto.HistoryNodeDTO;
import br.com.pds.streaming.framework.media.model.dto.LikeRatingDTO;
import br.com.pds.streaming.framework.media.model.entities.HistoryNode;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;

@Configuration
public class BlockburstModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new br.com.pds.streaming.framework.mapper.modelMapper.config.ModelMapperConfig().modelMapper();

        Converter<HistoryNode, HistoryNodeDTO> historyNodeDTOConverter = ctx -> {
            HistoryNode source = ctx.getSource();
            if (source.getMedia() == null) {
                throw new MissingOrInvalidMediaException(source);
            } else {
                if (source.getMedia() instanceof Movie) {

                    HistoryNodeDTO<MovieResponse> destination = new HistoryNodeDTO<>();
                    destination.setId(source.getId());
                    destination.setMedia(modelMapper.map(source.getMedia(), MovieResponse.class));
                    destination.setCurrentTime(source.getCurrentTime());
                    return destination;
                } else if (source.getMedia() instanceof Episode) {
                    HistoryNodeDTO<EpisodeDTO> destination = new HistoryNodeDTO<>();
                    destination.setId(source.getId());
                    destination.setMedia(modelMapper.map(source.getMedia(), EpisodeDTO.class));
                    destination.setCurrentTime(source.getCurrentTime());
                    return destination;
                } else {
                    throw new MissingOrInvalidMediaException(source);
                }
            }
        };

        final Converter<Movie, MovieResponse> movieConverter = ctx -> {

            var source = ctx.getSource();
            MovieResponse destination = new MovieResponse();

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
            TvShowResponse destination = new TvShowResponse();

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

        modelMapper.createTypeMap(HistoryNode.class, HistoryNodeDTO.class).setConverter(historyNodeDTOConverter);
        modelMapper.createTypeMap(HistoryNodeDTO.class, HistoryNode.class).addMapping(HistoryNodeDTO::getId, HistoryNode::setId);

        return modelMapper;
    }
}
