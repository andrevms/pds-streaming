package br.com.pds.streaming.yulearn.media.repositories;

import br.com.pds.streaming.yulearn.media.model.entities.VideoLesson;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VideoLessonRepository extends MongoRepository<VideoLesson, String> {
    List<VideoLesson> findByModuleId(String moduleId);
}
