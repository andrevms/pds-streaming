package br.com.pds.streaming.yulearn.media.services;

import br.com.pds.streaming.framework.exceptions.MediaTypeException;
import br.com.pds.streaming.framework.media.model.dto.MediaDTO;
import br.com.pds.streaming.framework.media.model.entities.Media;
import br.com.pds.streaming.framework.media.services.SearchService;
import br.com.pds.streaming.yulearn.mapper.modelMapper.YulearnMapper;
import br.com.pds.streaming.yulearn.media.model.dto.CourseDTO;
import br.com.pds.streaming.yulearn.media.model.dto.ModuleDTO;
import br.com.pds.streaming.yulearn.media.model.dto.TextLessonResponse;
import br.com.pds.streaming.yulearn.media.model.dto.VideoLessonResponse;
import br.com.pds.streaming.yulearn.media.model.entities.Course;
import br.com.pds.streaming.yulearn.media.model.entities.Module;
import br.com.pds.streaming.yulearn.media.model.entities.TextLesson;
import br.com.pds.streaming.yulearn.media.model.entities.VideoLesson;
import br.com.pds.streaming.yulearn.media.repositories.CourseRepository;
import br.com.pds.streaming.yulearn.media.repositories.ModuleRepository;
import br.com.pds.streaming.yulearn.media.repositories.TextLessonRepository;
import br.com.pds.streaming.yulearn.media.repositories.VideoLessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class YulearnSearchService extends SearchService {

    private final YulearnMapper mapper;

    @Autowired
    public YulearnSearchService(CourseRepository courseRepository, ModuleRepository moduleRepository, TextLessonRepository textLessonRepository, VideoLessonRepository videoLessonRepository, YulearnMapper mapper) {
        super(Set.of(courseRepository, moduleRepository, textLessonRepository, videoLessonRepository));
        this.mapper = mapper;
    }

    @Override
    public List<? extends MediaDTO> search(String keyWord) {

        List<Media> result = new ArrayList<>();

        result.addAll(searchByTitle(keyWord));
        result.addAll(searchByDescription(keyWord));
        result.addAll(searchByCategory(keyWord));

        Collections.shuffle(result);

        return result.stream().map(m -> {
            if (m instanceof Course) {
                return mapper.convertValue(m, CourseDTO.class);
            } else if (m instanceof Module) {
                return mapper.convertValue(m, ModuleDTO.class);
            } else if (m instanceof TextLesson) {
                return mapper.convertValue(m, TextLessonResponse.class);
            } else if (m instanceof VideoLesson) {
                return mapper.convertValue(m, VideoLessonResponse.class);
            } else {
                throw new MediaTypeException();
            }
        }).toList();
    }
}
