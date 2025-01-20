package br.com.pds.streaming.blockfy.mapper.modelMapper.config;

import br.com.pds.streaming.blockfy.media.model.dto.*;
import br.com.pds.streaming.blockfy.media.model.entities.*;
import br.com.pds.streaming.framework.mapper.modelMapper.config.ModelMapperConfig;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BlockfyModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapperConfig().modelMapper();

        modelMapper.createTypeMap(Music.class, MusicDTO.class).addMapping(Music::getId, MusicDTO::setId);
        modelMapper.createTypeMap(MusicDTO.class, Music.class).addMapping(MusicDTO::getId, Music::setId);

        modelMapper.createTypeMap(Podcast.class, PodcastDTO.class).addMapping(Podcast::getId, PodcastDTO::setId);
        modelMapper.createTypeMap(PodcastDTO.class, Podcast.class).addMapping(PodcastDTO::getId, Podcast::setId);

        return modelMapper;
    }
}
