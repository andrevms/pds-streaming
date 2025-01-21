package br.com.pds.streaming.yulearn.media.repositories;

import br.com.pds.streaming.yulearn.media.model.entities.TextLesson;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TextLessonRepository extends MongoRepository<TextLesson, String> {
    List<TextLesson> findByModuleId(String moduleId);
}
