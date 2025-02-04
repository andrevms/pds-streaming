package br.com.pds.streaming.yulearn.media.repositories;

import br.com.pds.streaming.framework.media.repositories.MediaRepository;
import br.com.pds.streaming.yulearn.media.model.entities.Module;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ModuleRepository extends MediaRepository<Module, String> {
    List<Module> findByCourseId(String courseId);
}
