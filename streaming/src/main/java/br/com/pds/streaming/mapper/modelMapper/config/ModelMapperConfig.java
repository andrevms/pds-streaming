package br.com.pds.streaming.mapper.modelMapper.config;

import br.com.pds.streaming.media.model.dto.*;
import br.com.pds.streaming.media.model.entities.*;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();

        modelMapper.createTypeMap(Episode.class, EpisodeDTO.class)
                .addMapping(Episode::getId, EpisodeDTO::setId);

        modelMapper.createTypeMap(EpisodeDTO.class, Episode.class)
                .addMapping(EpisodeDTO::getId, Episode::setId);

        modelMapper.createTypeMap(History.class, HistoryDTO.class)
                .addMapping(History::getId, HistoryDTO::setId);

        modelMapper.createTypeMap(HistoryDTO.class, History.class)
                .addMapping(HistoryDTO::getId, History::setId);

        modelMapper.createTypeMap(HistoryNode.class, HistoryNodeWithEpisodeDTO.class)
                .addMapping(HistoryNode::getId, HistoryNodeWithEpisodeDTO::setId);

        modelMapper.createTypeMap(HistoryNode.class, HistoryNodeWithMovieDTO.class)
                .addMapping(HistoryNode::getId, HistoryNodeWithMovieDTO::setId);

        modelMapper.createTypeMap(HistoryNodeWithBothDTO.class, HistoryNode.class)
                .addMapping(HistoryNodeWithBothDTO::getId, HistoryNode::setId);

        modelMapper.createTypeMap(Movie.class, MovieDTO.class)
                .addMapping(Movie::getId, MovieDTO::setId);

        modelMapper.createTypeMap(MovieDTO.class, Movie.class)
                .addMapping(MovieDTO::getId, Movie::setId);

        modelMapper.createTypeMap(Rating.class, RatingDTO.class)
                .addMapping(Rating::getId, RatingDTO::setId);

        modelMapper.createTypeMap(RatingDTO.class, Rating.class)
                .addMapping(RatingDTO::getId, Rating::setId);

        modelMapper.createTypeMap(Season.class, SeasonDTO.class)
                .addMapping(Season::getId, SeasonDTO::setId);

        modelMapper.createTypeMap(SeasonDTO.class, Season.class)
                .addMapping(SeasonDTO::getId, Season::setId);

        return modelMapper;
    }
}
