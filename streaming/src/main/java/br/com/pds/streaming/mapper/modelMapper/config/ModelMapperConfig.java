package br.com.pds.streaming.mapper.modelMapper.config;

import br.com.pds.streaming.authentication.model.dto.domain.UserDTO;
import br.com.pds.streaming.authentication.model.entities.User;
import br.com.pds.streaming.exceptions.MissingOrInvalidMediaException;
import br.com.pds.streaming.media.model.dto.*;
import br.com.pds.streaming.media.model.entities.*;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();

        Converter<HistoryNode, HistoryNodeDTO> historyNodeDTOConverter = ctx -> {
            HistoryNode source = ctx.getSource();
            if (source.getMedia() == null) {
                throw new MissingOrInvalidMediaException(source);
            } else {
                if (source.getMedia() instanceof Movie) {
                    HistoryNodeDTO<MovieDTO> destination = new HistoryNodeDTO<>();
                    destination.setId(source.getId());
                    destination.setMedia(modelMapper.map(source.getMedia(), MovieDTO.class));
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

        modelMapper.createTypeMap(Episode.class, EpisodeDTO.class).addMapping(Episode::getId, EpisodeDTO::setId);
        modelMapper.createTypeMap(EpisodeDTO.class, Episode.class).addMapping(EpisodeDTO::getId, Episode::setId);

        modelMapper.createTypeMap(History.class, HistoryDTO.class).addMapping(History::getId, HistoryDTO::setId);
        modelMapper.createTypeMap(HistoryDTO.class, History.class).addMapping(HistoryDTO::getId, History::setId);

        modelMapper.createTypeMap(HistoryNode.class, HistoryNodeDTO.class).setConverter(historyNodeDTOConverter);
        modelMapper.createTypeMap(HistoryNodeDTO.class, HistoryNode.class).addMapping(HistoryNodeDTO::getId, HistoryNode::setId);

//        modelMapper.createTypeMap(Media.class, MediaDTO.class).addMapping(Media::getId, MediaDTO::setId);
//        modelMapper.createTypeMap(MediaDTO.class, Media.class).addMapping(MediaDTO::getId, Media::setId);

        modelMapper.createTypeMap(Movie.class, MovieDTO.class).addMapping(Movie::getId, MovieDTO::setId);
        modelMapper.createTypeMap(MovieDTO.class, Movie.class).addMapping(MovieDTO::getId, Movie::setId);

        modelMapper.createTypeMap(Rating.class, RatingDTO.class).addMapping(Rating::getId, RatingDTO::setId);
        modelMapper.createTypeMap(RatingDTO.class, Rating.class).addMapping(RatingDTO::getId, Rating::setId);

        modelMapper.createTypeMap(Season.class, SeasonDTO.class).addMapping(Season::getId, SeasonDTO::setId);
        modelMapper.createTypeMap(SeasonDTO.class, Season.class).addMapping(SeasonDTO::getId, Season::setId);

        modelMapper.createTypeMap(User.class, UserDTO.class).addMapping(User::getId, UserDTO::setId);
        modelMapper.createTypeMap(UserDTO.class, User.class).addMapping(UserDTO::getId, User::setId);

        return modelMapper;
    }
}
