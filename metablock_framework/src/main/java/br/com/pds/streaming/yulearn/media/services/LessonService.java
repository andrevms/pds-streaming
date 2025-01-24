package br.com.pds.streaming.yulearn.media.services;

import br.com.pds.streaming.framework.media.model.entities.Media;
import br.com.pds.streaming.yulearn.mapper.modelMapper.YulearnMapper;
import br.com.pds.streaming.yulearn.media.model.dto.LessonDTO;
import br.com.pds.streaming.yulearn.media.model.dto.TextLessonResponse;
import br.com.pds.streaming.yulearn.media.model.dto.VideoLessonResponse;
import br.com.pds.streaming.yulearn.media.model.entities.Lesson;
import br.com.pds.streaming.yulearn.media.model.entities.TextLesson;
import br.com.pds.streaming.yulearn.media.model.entities.VideoLesson;
import br.com.pds.streaming.yulearn.media.repositories.TextLessonRepository;
import br.com.pds.streaming.yulearn.media.repositories.VideoLessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class LessonService {

    private TextLessonRepository textLessonRepository;
    private VideoLessonRepository videoLessonRepository;
    private YulearnMapper mapper;

    @Autowired
    public LessonService(TextLessonRepository textLessonRepository, VideoLessonRepository videoLessonRepository, YulearnMapper mapper) {
        this.textLessonRepository = textLessonRepository;
        this.videoLessonRepository = videoLessonRepository;
        this.mapper = mapper;
    }

    public List<LessonDTO> findAll() {

        List<Lesson> lessons = new ArrayList<>();

        var textLessons = textLessonRepository.findAll();
        var videoLessons = videoLessonRepository.findAll();

        lessons.addAll(textLessons);
        lessons.addAll(videoLessons);

        return lessons.stream().sorted(Comparator.comparing(Media::getTitle)).map(l -> {
            if (l instanceof TextLesson) return mapper.convertValue(l, TextLessonResponse.class);
            if (l instanceof VideoLesson) return mapper.convertValue(l, VideoLessonResponse.class);
            return null;
        }).toList();
    }
}
