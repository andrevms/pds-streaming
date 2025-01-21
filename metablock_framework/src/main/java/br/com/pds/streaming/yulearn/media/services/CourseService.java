package br.com.pds.streaming.yulearn.media.services;

import br.com.pds.streaming.framework.cloud.services.CloudStorageService;
import br.com.pds.streaming.framework.exceptions.EntityNotFoundException;
import br.com.pds.streaming.framework.media.repositories.StarRatingRepository;
import br.com.pds.streaming.yulearn.mapper.modelMapper.YulearnMapper;
import br.com.pds.streaming.yulearn.media.model.dto.CourseDTO;
import br.com.pds.streaming.yulearn.media.model.entities.Course;
import br.com.pds.streaming.yulearn.media.model.entities.Module;
import br.com.pds.streaming.yulearn.media.repositories.CourseRepository;
import br.com.pds.streaming.yulearn.media.repositories.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.pds.streaming.framework.media.util.VerifyHelper.*;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final ModuleService moduleService;
    private final StarRatingRepository ratingRepository;
    private final YulearnMapper mapper;
    private final CloudStorageService cloudStorageService;
    private final ModuleRepository moduleRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, ModuleService moduleService, StarRatingRepository ratingRepository, YulearnMapper mapper, CloudStorageService cloudStorageService, ModuleRepository moduleRepository) {
        this.courseRepository = courseRepository;
        this.moduleService = moduleService;
        this.ratingRepository = ratingRepository;
        this.mapper = mapper;
        this.cloudStorageService = cloudStorageService;
        this.moduleRepository = moduleRepository;
    }

    public List<CourseDTO> findAll() {
        return mapper.convertList(courseRepository.findAll(), CourseDTO.class);
    }

    public CourseDTO findById(String id) {
        return mapper.convertValue(courseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Course.class)), CourseDTO.class);
    }

    public CourseDTO insert(CourseDTO courseDTO) {

        verifyFileUrl(courseDTO);

        var course = mapper.convertValue(courseDTO, Course.class);

        var createdCourse = courseRepository.save(course);

        return mapper.convertValue(createdCourse, CourseDTO.class);
    }

    public CourseDTO update(CourseDTO courseDTO, String id) {

        verifyFileUrl(courseDTO);

        Course course = courseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Course.class));

        course.setTitle(courseDTO.getTitle());
        course.setDescription(courseDTO.getDescription());
        course.setThumbnailUrl(courseDTO.getThumbnailUrl());
        course.setAnimationUrl(courseDTO.getAnimationUrl());
        course.setCategories(courseDTO.getCategories());

        var updatedCourse = courseRepository.save(course);

        return mapper.convertValue(updatedCourse, CourseDTO.class);
    }

    public CourseDTO patch(CourseDTO courseDTO, String id) {

        var course = courseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Course.class));

        if (course.getTitle() != null) course.setTitle(courseDTO.getTitle());
        if (course.getDescription() != null) course.setDescription(courseDTO.getDescription());

        if (course.getThumbnailUrl() != null) {
            verifyThumbnailUrl(courseDTO);
            course.setThumbnailUrl(courseDTO.getThumbnailUrl());
        }

        if (course.getAnimationUrl() != null) {
            verifyAnimationUrl(courseDTO);
            course.setAnimationUrl(courseDTO.getAnimationUrl());
        }

        if (course.getCategories() != null) {
            if (!course.getCategories().isEmpty()) course.setCategories(course.getCategories());
        }

        var patchedCourse = courseRepository.save(course);

        return mapper.convertValue(patchedCourse, CourseDTO.class);
    }

    public void delete(String id) {

        deleteOrphanModules(id);
        deleteOrphanRatings(id);

        var course = findById(id);
        var courseThumbnail = course.getThumbnailUrl();
        var courseAnimation = course.getAnimationUrl();

        cloudStorageService.deleteFile(courseThumbnail);
        cloudStorageService.deleteFile(courseAnimation);

        courseRepository.deleteById(id);
    }

    private void deleteOrphanModules(String courseId) {

        var modules = moduleRepository.findByCourseId(courseId);

        modules.forEach(module -> moduleService.deleteOrphanLessons(module.getId()));

        moduleRepository.deleteAll(mapper.convertList(modules, Module.class));
    }

    private void deleteOrphanRatings(String courseId) {

        var course = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException(Course.class));

        ratingRepository.deleteAll(ratingRepository.findAll().stream().filter(r -> course.getRatings().contains(r)).toList());
    }

    private void verifyFileUrl(CourseDTO courseDTO) {
        verifyThumbnailUrl(courseDTO);
        verifyAnimationUrl(courseDTO);
    }
}
