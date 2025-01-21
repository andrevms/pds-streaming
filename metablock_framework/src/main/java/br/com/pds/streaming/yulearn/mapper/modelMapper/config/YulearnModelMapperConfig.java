package br.com.pds.streaming.yulearn.mapper.modelMapper.config;

import br.com.pds.streaming.framework.mapper.modelMapper.config.ModelMapperConfig;
import br.com.pds.streaming.framework.media.model.dto.StarRatingDTO;
import br.com.pds.streaming.framework.media.model.entities.StarRating;
import br.com.pds.streaming.yulearn.media.model.dto.*;
import br.com.pds.streaming.yulearn.media.model.entities.*;
import br.com.pds.streaming.yulearn.media.model.entities.Module;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YulearnModelMapperConfig {

    @Bean
    public ModelMapper getYulearnModelMapper() {

        ModelMapper modelMapper = new ModelMapperConfig().modelMapper();

        final Converter<Course, CourseDTO> courseDTOConverter = ctx -> {
            var source = ctx.getSource();
            var destination = ctx.getDestination();
            destination.setId(source.getId());
            destination.setTitle(source.getTitle());
            destination.setDescription(source.getDescription());
            destination.setThumbnailUrl(source.getThumbnailUrl());
            destination.setAnimationUrl(source.getAnimationUrl());
            destination.setCategories(source.getCategories());
            destination.setModules(source.getModules().stream().map(module -> modelMapper.map(module, ModuleDTO.class)).toList());
            destination.setRatings(source.getRatings().stream().map(rating -> modelMapper.map(rating, StarRatingDTO.class)).toList());
            destination.setRatingsAverage(source.getRatings().stream().mapToDouble(StarRating::getStarRating).sum());
            return destination;
        };

        modelMapper.createTypeMap(Course.class, CourseDTO.class).addMapping(Course::getId, CourseDTO::setId);
        modelMapper.createTypeMap(CourseDTO.class, Course.class).addMapping(CourseDTO::getId, Course::setId);

        modelMapper.createTypeMap(Module.class, ModuleDTO.class).addMapping(Module::getId, ModuleDTO::setId);
        modelMapper.createTypeMap(ModuleDTO.class, Module.class).addMapping(ModuleDTO::getId, Module::setId);

        modelMapper.createTypeMap(TextLesson.class, TextLessonDTO.class).addMapping(TextLesson::getId, TextLessonDTO::setId);
        modelMapper.createTypeMap(TextLessonDTO.class, TextLesson.class).addMapping(TextLessonDTO::getId, TextLesson::setId);

        modelMapper.createTypeMap(VideoLesson.class, VideoLessonDTO.class).addMapping(VideoLesson::getId, VideoLessonDTO::setId);
        modelMapper.createTypeMap(VideoLessonDTO.class, VideoLesson.class).addMapping(VideoLessonDTO::getId, VideoLesson::setId);

        return modelMapper;
    }
}
