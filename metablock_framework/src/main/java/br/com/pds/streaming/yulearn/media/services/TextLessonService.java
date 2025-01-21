package br.com.pds.streaming.yulearn.media.services;

import br.com.pds.streaming.framework.cloud.services.CloudStorageService;
import br.com.pds.streaming.framework.exceptions.EntityNotFoundException;
import br.com.pds.streaming.framework.exceptions.InvalidVideoException;
import br.com.pds.streaming.framework.media.util.FileExtensionValidator;
import br.com.pds.streaming.yulearn.mapper.modelMapper.YulearnMapper;
import br.com.pds.streaming.yulearn.media.model.dto.TextLessonRequest;
import br.com.pds.streaming.yulearn.media.model.dto.TextLessonResponse;
import br.com.pds.streaming.yulearn.media.model.entities.Module;
import br.com.pds.streaming.yulearn.media.model.entities.TextLesson;
import br.com.pds.streaming.yulearn.media.repositories.ModuleRepository;
import br.com.pds.streaming.yulearn.media.repositories.TextLessonRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.pds.streaming.framework.media.util.VerifyHelper.verifyAnimationUrl;
import static br.com.pds.streaming.framework.media.util.VerifyHelper.verifyThumbnailUrl;

@Service
public class TextLessonService {

    private final TextLessonRepository textLessonRepository;
    private final YulearnMapper mapper;
    private final CloudStorageService cloudStorageService;
    private final ModuleRepository moduleRepository;

    @Autowired
    public TextLessonService(TextLessonRepository textLessonRepository, YulearnMapper mapper, CloudStorageService cloudStorageService, ModuleRepository moduleRepository) {
        this.textLessonRepository = textLessonRepository;
        this.mapper = mapper;
        this.cloudStorageService = cloudStorageService;
        this.moduleRepository = moduleRepository;
    }

    public List<TextLessonResponse> findAll() {
        return mapper.convertList(textLessonRepository.findAll(), TextLessonResponse.class);
    }

    public TextLessonResponse findById(String id) {
        return mapper.convertValue(textLessonRepository.findById(id), TextLessonResponse.class);
    }

    public TextLessonResponse insert(TextLessonRequest textLessonRequest, String moduleId) {

        verifyFileUrl(textLessonRequest);

        var textLesson = mapper.convertValue(textLessonRequest, TextLesson.class);
        textLesson.setModuleId(moduleId);
        var createdTextLesson = textLessonRepository.save(textLesson);

        var module = moduleRepository.findById(moduleId).orElseThrow(() -> new EntityNotFoundException(Module.class));
        module.getLessons().add(textLesson);
        moduleRepository.save(module);

        return mapper.convertValue(createdTextLesson, TextLessonResponse.class);
    }

    public TextLessonResponse update(TextLessonRequest textLessonRequest, String id) {

        verifyFileUrl(textLessonRequest);

        var textLesson = textLessonRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(TextLesson.class));

        textLesson.setTitle(textLessonRequest.getTitle());
        textLesson.setDescription(textLessonRequest.getDescription());
        textLesson.setPdfUrl(textLessonRequest.getPdfUrl());
        textLesson.setThumbnailUrl(textLessonRequest.getThumbnailUrl());
        textLesson.setAnimationUrl(textLessonRequest.getAnimationUrl());

        var updatedTextLesson = textLessonRepository.save(textLesson);

        return mapper.convertValue(updatedTextLesson, TextLessonResponse.class);
    }

    public TextLessonResponse patch(TextLessonRequest textLessonRequest, String id) {

        var textLesson = textLessonRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(TextLesson.class));

        if (textLessonRequest.getTitle() != null) textLesson.setTitle(textLessonRequest.getTitle());
        if (textLessonRequest.getDescription() != null) textLesson.setDescription(textLessonRequest.getDescription());

        if (textLessonRequest.getPdfUrl() != null) {
            verifyPdfUrl(textLessonRequest);
            textLesson.setPdfUrl(textLessonRequest.getPdfUrl());
        }

        if (textLessonRequest.getThumbnailUrl() != null) {
            verifyThumbnailUrl(textLessonRequest);
            textLesson.setThumbnailUrl(textLessonRequest.getThumbnailUrl());
        }

        if (textLessonRequest.getAnimationUrl() != null) {
            verifyAnimationUrl(textLessonRequest);
            textLesson.setAnimationUrl(textLessonRequest.getAnimationUrl());
        }

        var patchedTextLesson = textLessonRepository.save(textLesson);

        return mapper.convertValue(patchedTextLesson, TextLessonResponse.class);
    }

    public void delete(String id) {

        var textLesson = findById(id);
        var textLessonPdf = textLesson.getPdfUrl();
        var textLessonThumbnail = textLesson.getThumbnailUrl();
        var textLessonAnimation = textLesson.getAnimationUrl();

        cloudStorageService.deleteFile(textLessonPdf);
        cloudStorageService.deleteFile(textLessonThumbnail);
        cloudStorageService.deleteFile(textLessonAnimation);

        textLessonRepository.deleteById(id);
    }

    private void verifyPdfUrl(TextLessonRequest textLessonRequest) {
        if (!FileExtensionValidator.validatePdfFileExtension(textLessonRequest.getPdfUrl())) throw new InvalidVideoException(textLessonRequest.getPdfUrl());
    }

    private void verifyFileUrl(@NotNull TextLessonRequest textLessonRequest) {
        verifyPdfUrl(textLessonRequest);
        verifyThumbnailUrl(textLessonRequest);
        verifyAnimationUrl(textLessonRequest);
    }
}
