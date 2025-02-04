package br.com.pds.streaming.framework.mapper.modelMapper.config;

import br.com.pds.streaming.framework.authentication.model.dto.domain.UserDTO;
import br.com.pds.streaming.framework.authentication.model.entities.User;
import br.com.pds.streaming.framework.media.model.dto.*;
import br.com.pds.streaming.framework.media.model.entities.*;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean(name = "frameworkModelMapperConfig")
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();

        modelMapper.createTypeMap(History.class, HistoryDTO.class).addMapping(History::getId, HistoryDTO::setId);
        modelMapper.createTypeMap(HistoryDTO.class, History.class).addMapping(HistoryDTO::getId, History::setId);

        modelMapper.createTypeMap(LikeRating.class, LikeRatingDTO.class).addMapping(LikeRating::getId, LikeRatingDTO::setId);
        modelMapper.createTypeMap(LikeRatingDTO.class, LikeRating.class).addMapping(LikeRatingDTO::getId, LikeRating::setId);


        modelMapper.createTypeMap(Media.class, MediaDTO.class).addMapping(Media::getId, MediaDTO::setId);
        modelMapper.createTypeMap(MediaDTO.class, Media.class).addMapping(MediaDTO::getId, Media::setId);

        modelMapper.createTypeMap(Rating.class, RatingDTO.class).addMapping(Rating::getId, RatingDTO::setId);
        modelMapper.createTypeMap(RatingDTO.class, Rating.class).addMapping(RatingDTO::getId, Rating::setId);

        modelMapper.createTypeMap(StarRating.class, StarRatingDTO.class).addMapping(StarRating::getId, StarRatingDTO::setId);
        modelMapper.createTypeMap(StarRatingDTO.class, StarRating.class).addMapping(StarRatingDTO::getId, StarRating::setId);

        modelMapper.createTypeMap(User.class, UserDTO.class).addMapping(User::getId, UserDTO::setId);
        modelMapper.createTypeMap(UserDTO.class, User.class).addMapping(UserDTO::getId, User::setId);

        return modelMapper;
    }
}
