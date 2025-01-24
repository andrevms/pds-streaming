package br.com.pds.streaming.yulearn.media.services;

import br.com.pds.streaming.framework.cloud.services.CloudStorageService;
import br.com.pds.streaming.framework.exceptions.EntityNotFoundException;
import br.com.pds.streaming.yulearn.mapper.modelMapper.YulearnMapper;
import br.com.pds.streaming.yulearn.media.model.dto.ModuleDTO;
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

import java.util.List;

import static br.com.pds.streaming.framework.media.util.FileExtensionVerifier.*;

@Service
public class ModuleService {

    private final ModuleRepository moduleRepository;
    private final CourseRepository courseRepository;
    private final TextLessonService textLessonService;
    private final VideoLessonService videoLessonService;
    private final YulearnMapper mapper;
    private final CloudStorageService cloudStorageService;
    private final TextLessonRepository textLessonRepository;
    private final VideoLessonRepository videoLessonRepository;

    @Autowired
    public ModuleService(ModuleRepository moduleRepository, CourseRepository courseRepository, TextLessonService textLessonService, VideoLessonService videoLessonService, YulearnMapper mapper, CloudStorageService cloudStorageService, TextLessonRepository textLessonRepository, VideoLessonRepository videoLessonRepository) {
        this.moduleRepository = moduleRepository;
        this.courseRepository = courseRepository;
        this.textLessonService = textLessonService;
        this.videoLessonService = videoLessonService;
        this.mapper = mapper;
        this.cloudStorageService = cloudStorageService;
        this.textLessonRepository = textLessonRepository;
        this.videoLessonRepository = videoLessonRepository;
    }

    public List<ModuleDTO> findAll() {
        return mapper.convertList(moduleRepository.findAll(), ModuleDTO.class);
    }

    public List<ModuleDTO> findByCourseId(String courseId) {
        return mapper.convertList(moduleRepository.findByCourseId(courseId), ModuleDTO.class);
    }

    public ModuleDTO findById(String id) {
        var module = moduleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Module.class));
        return mapper.convertValue(module, ModuleDTO.class);
    }

    public ModuleDTO insert(ModuleDTO moduleDTO, String courseId) {

        verifyFilesUrl(moduleDTO);

        var module = mapper.convertValue(moduleDTO, Module.class);
        module.setCourseId(courseId);
        var createdModule = moduleRepository.save(module);

        var course = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException(Course.class));
        course.getModules().add(module);
        courseRepository.save(course);

        return mapper.convertValue(createdModule, ModuleDTO.class);
    }

    public ModuleDTO update(ModuleDTO moduleDTO, String id) {

        verifyFilesUrl(moduleDTO);

        var module = moduleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Module.class));

        module.setTitle(moduleDTO.getTitle());
        module.setDescription(moduleDTO.getDescription());
        module.setThumbnailUrl(moduleDTO.getThumbnailUrl());
        module.setAnimationUrl(moduleDTO.getAnimationUrl());

        var updatedModule = moduleRepository.save(module);

        return mapper.convertValue(updatedModule, ModuleDTO.class);
    }

    public ModuleDTO patch(ModuleDTO moduleDTO, String id) {

        var module = moduleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Module.class));

        if (module.getTitle() != null) module.setTitle(moduleDTO.getTitle());
        if (module.getDescription() != null) module.setDescription(moduleDTO.getDescription());

        if (module.getThumbnailUrl() != null) {
            verifyThumbnailUrl(moduleDTO);
            module.setThumbnailUrl(moduleDTO.getThumbnailUrl());
        }

        if (module.getAnimationUrl() != null) {
            verifyAnimationUrl(moduleDTO);
            module.setAnimationUrl(moduleDTO.getAnimationUrl());
        }

        var patchedModule = moduleRepository.save(module);

        return mapper.convertValue(patchedModule, ModuleDTO.class);
    }

    public void delete(String id) {

        deleteOrphanLessons(id);

        var module = findById(id);
        var moduleThumbnail = module.getThumbnailUrl();
        var moduleAnimation = module.getAnimationUrl();

        cloudStorageService.deleteFile(moduleThumbnail);
        cloudStorageService.deleteFile(moduleAnimation);

        moduleRepository.deleteById(id);
    }

    protected void deleteOrphanLessons(String moduleId) {
        textLessonRepository.deleteAll(mapper.convertList(textLessonRepository.findByModuleId(moduleId), TextLesson.class));
        videoLessonRepository.deleteAll(mapper.convertList(videoLessonRepository.findByModuleId(moduleId), VideoLesson.class));
    }

    private void verifyFilesUrl(ModuleDTO moduleDTO) {
        verifyThumbnailUrl(moduleDTO);
        verifyAnimationUrl(moduleDTO);
    }
}
