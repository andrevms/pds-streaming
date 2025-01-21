package br.com.pds.streaming.yulearn.media.services;

import br.com.pds.streaming.framework.cloud.services.CloudStorageService;
import br.com.pds.streaming.framework.exceptions.EntityNotFoundException;
import br.com.pds.streaming.framework.exceptions.InvalidVideoException;
import br.com.pds.streaming.framework.media.util.FileExtensionValidator;
import br.com.pds.streaming.yulearn.mapper.modelMapper.YulearnMapper;
import br.com.pds.streaming.yulearn.media.model.dto.VideoLessonDTO;
import br.com.pds.streaming.yulearn.media.model.entities.Module;
import br.com.pds.streaming.yulearn.media.model.entities.VideoLesson;
import br.com.pds.streaming.yulearn.media.repositories.ModuleRepository;
import br.com.pds.streaming.yulearn.media.repositories.VideoLessonRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.pds.streaming.framework.media.util.VerifyHelper.verifyAnimationUrl;
import static br.com.pds.streaming.framework.media.util.VerifyHelper.verifyThumbnailUrl;

@Service
public class VideoLessonService {

    private final VideoLessonRepository videoLessonRepository;
    private final YulearnMapper mapper;
    private final CloudStorageService cloudStorageService;
    private final ModuleRepository moduleRepository;

    @Autowired
    public VideoLessonService(VideoLessonRepository videoLessonRepository, YulearnMapper mapper, CloudStorageService cloudStorageService, ModuleRepository moduleRepository) {
        this.videoLessonRepository = videoLessonRepository;
        this.mapper = mapper;
        this.cloudStorageService = cloudStorageService;
        this.moduleRepository = moduleRepository;
    }

    public List<VideoLessonDTO> findAll() {
        return mapper.convertList(videoLessonRepository.findAll(), VideoLessonDTO.class);
    }

    public VideoLessonDTO findById(String id) {
        return mapper.convertValue(videoLessonRepository.findById(id), VideoLessonDTO.class);
    }

    public VideoLessonDTO insert(VideoLessonDTO videoLessonDTO, String moduleId) {

        verifyFileUrl(videoLessonDTO);

        var videoLesson = mapper.convertValue(videoLessonDTO, VideoLesson.class);
        videoLesson.setModuleId(moduleId);
        var createdVideoLesson = videoLessonRepository.save(videoLesson);

        var module = moduleRepository.findById(moduleId).orElseThrow(() -> new EntityNotFoundException(Module.class));
        module.getLessons().add(videoLesson);
        moduleRepository.save(module);

        return mapper.convertValue(createdVideoLesson, VideoLessonDTO.class);
    }

    public VideoLessonDTO update(VideoLessonDTO videoLessonDTO, String id) {

        verifyFileUrl(videoLessonDTO);

        var videoLesson = videoLessonRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(VideoLesson.class));

        videoLesson.setTitle(videoLessonDTO.getTitle());
        videoLesson.setDescription(videoLessonDTO.getDescription());
        videoLesson.setVideoUrl(videoLessonDTO.getVideoUrl());
        videoLesson.setThumbnailUrl(videoLessonDTO.getThumbnailUrl());

        var updatedVideoLesson = videoLessonRepository.save(videoLesson);

        return mapper.convertValue(updatedVideoLesson, VideoLessonDTO.class);
    }

    public VideoLessonDTO patch(VideoLessonDTO videoLessonDTO, String id) {

        var videoLesson = videoLessonRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(VideoLesson.class));

        if (videoLesson.getTitle() != null) videoLesson.setTitle(videoLesson.getTitle());
        if (videoLesson.getDescription() != null) videoLesson.setDescription(videoLesson.getDescription());

        if (videoLesson.getVideoUrl() != null) {
            verifyVideoUrl(videoLessonDTO);
            videoLesson.setVideoUrl(videoLessonDTO.getVideoUrl());
        }

        if (videoLesson.getThumbnailUrl() != null) {
            verifyThumbnailUrl(videoLessonDTO);
            videoLesson.setThumbnailUrl(videoLessonDTO.getThumbnailUrl());
        }

        if (videoLesson.getAnimationUrl() != null) {
            verifyAnimationUrl(videoLessonDTO);
            videoLesson.setAnimationUrl(videoLessonDTO.getAnimationUrl());
        }

        var patchedVideoLesson = videoLessonRepository.save(videoLesson);

        return mapper.convertValue(patchedVideoLesson, VideoLessonDTO.class);
    }

    public void delete(String id) {

        var videoLesson = findById(id);
        var videoLessonVideo = videoLesson.getVideoUrl();
        var videoLessonThumbnail = videoLesson.getThumbnailUrl();
        var videoLessonAnimation = videoLesson.getAnimationUrl();

        cloudStorageService.deleteFile(videoLessonVideo);
        cloudStorageService.deleteFile(videoLessonThumbnail);
        cloudStorageService.deleteFile(videoLessonAnimation);

        videoLessonRepository.deleteById(id);
    }

    private void verifyVideoUrl(VideoLessonDTO videoLessonDTO) {
        if (!FileExtensionValidator.validateVideoFileExtension(videoLessonDTO.getVideoUrl())) throw new InvalidVideoException(videoLessonDTO.getVideoUrl());
    }

    private void verifyFileUrl(@NotNull VideoLessonDTO videoLessonDTO) {
        verifyVideoUrl(videoLessonDTO);
        verifyThumbnailUrl(videoLessonDTO);
        verifyAnimationUrl(videoLessonDTO);
    }
}
