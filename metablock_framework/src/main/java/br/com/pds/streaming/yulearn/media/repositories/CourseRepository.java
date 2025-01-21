package br.com.pds.streaming.yulearn.media.repositories;

import br.com.pds.streaming.yulearn.media.model.entities.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CourseRepository extends MongoRepository<Course, String> {
}
