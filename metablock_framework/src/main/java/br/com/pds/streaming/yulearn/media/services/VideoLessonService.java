package br.com.pds.streaming.yulearn.media.services;

import br.com.pds.streaming.framework.cloud.services.CloudStorageService;
import br.com.pds.streaming.framework.exceptions.EntityNotFoundException;
import br.com.pds.streaming.yulearn.mapper.modelMapper.YulearnMapper;
import br.com.pds.streaming.yulearn.media.model.dto.VideoLessonRequest;
import br.com.pds.streaming.yulearn.media.model.dto.VideoLessonResponse;
import br.com.pds.streaming.yulearn.media.model.entities.Module;
import br.com.pds.streaming.yulearn.media.model.entities.VideoLesson;
import br.com.pds.streaming.yulearn.media.repositories.ModuleRepository;
import br.com.pds.streaming.yulearn.media.repositories.VideoLessonRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.pds.streaming.framework.media.util.FileExtensionVerifier.verifyAnimationUrl;
import static br.com.pds.streaming.framework.media.util.FileExtensionVerifier.verifyThumbnailUrl;
import static br.com.pds.streaming.yulearn.media.util.FileExtensionVerifier.verifyVideoUrl;

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

    public List<VideoLessonResponse> findAll() {
        return mapper.convertList(videoLessonRepository.findAll(), VideoLessonResponse.class);
    }

    public VideoLessonResponse findById(String id) {
        return mapper.convertValue(videoLessonRepository.findById(id), VideoLessonResponse.class);
    }

    public VideoLessonResponse insert(VideoLessonRequest videoLessonRequest, String moduleId) {

        verifyFileUrl(videoLessonRequest);

        var videoLesson = mapper.convertValue(videoLessonRequest, VideoLesson.class);
        videoLesson.setModuleId(moduleId);
        var createdVideoLesson = videoLessonRepository.save(videoLesson);

        var module = moduleRepository.findById(moduleId).orElseThrow(() -> new EntityNotFoundException(Module.class));
        module.getLessons().add(videoLesson);
        moduleRepository.save(module);

        return mapper.convertValue(createdVideoLesson, VideoLessonResponse.class);
    }

    public VideoLessonResponse update(VideoLessonRequest videoLessonRequest, String id) {

        verifyFileUrl(videoLessonRequest);

        var videoLesson = videoLessonRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(VideoLesson.class));

        videoLesson.setTitle(videoLessonRequest.getTitle());
        videoLesson.setDescription(videoLessonRequest.getDescription());
        videoLesson.setVideoUrl(videoLessonRequest.getVideoUrl());
        videoLesson.setThumbnailUrl(videoLessonRequest.getThumbnailUrl());

        var updatedVideoLesson = videoLessonRepository.save(videoLesson);

        return mapper.convertValue(updatedVideoLesson, VideoLessonResponse.class);
    }

    public VideoLessonResponse patch(VideoLessonRequest videoLessonRequest, String id) {

        var videoLesson = videoLessonRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(VideoLesson.class));

        if (videoLesson.getTitle() != null) videoLesson.setTitle(videoLesson.getTitle());
        if (videoLesson.getDescription() != null) videoLesson.setDescription(videoLesson.getDescription());

        if (videoLesson.getVideoUrl() != null) {
            verifyVideoUrl(videoLessonRequest);
            videoLesson.setVideoUrl(videoLessonRequest.getVideoUrl());
        }

        if (videoLesson.getThumbnailUrl() != null) {
            verifyThumbnailUrl(videoLessonRequest);
            videoLesson.setThumbnailUrl(videoLessonRequest.getThumbnailUrl());
        }

        if (videoLesson.getAnimationUrl() != null) {
            verifyAnimationUrl(videoLessonRequest);
            videoLesson.setAnimationUrl(videoLessonRequest.getAnimationUrl());
        }

        var patchedVideoLesson = videoLessonRepository.save(videoLesson);

        return mapper.convertValue(patchedVideoLesson, VideoLessonResponse.class);
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

    private void verifyFileUrl(@NotNull VideoLessonRequest videoLessonRequest) {
        verifyVideoUrl(videoLessonRequest);
        verifyThumbnailUrl(videoLessonRequest);
        verifyAnimationUrl(videoLessonRequest);
    }
}
