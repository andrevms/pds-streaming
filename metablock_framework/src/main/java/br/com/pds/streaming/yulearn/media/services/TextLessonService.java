package br.com.pds.streaming.yulearn.media.services;

import br.com.pds.streaming.framework.cloud.services.CloudStorageService;
import br.com.pds.streaming.framework.exceptions.EntityNotFoundException;
import br.com.pds.streaming.framework.exceptions.InvalidVideoException;
import br.com.pds.streaming.framework.media.util.FileExtensionValidator;
import br.com.pds.streaming.yulearn.mapper.modelMapper.YulearnMapper;
import br.com.pds.streaming.yulearn.media.model.dto.TextLessonDTO;
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

    public List<TextLessonDTO> findAll() {
        return mapper.convertList(textLessonRepository.findAll(), TextLessonDTO.class);
    }

    public TextLessonDTO findById(String id) {
        return mapper.convertValue(textLessonRepository.findById(id), TextLessonDTO.class);
    }

    public TextLessonDTO insert(TextLessonDTO textLessonDTO, String moduleId) {

        verifyFileUrl(textLessonDTO);

        var textLesson = mapper.convertValue(textLessonDTO, TextLesson.class);
        textLesson.setModuleId(moduleId);
        var createdTextLesson = textLessonRepository.save(textLesson);

        var module = moduleRepository.findById(moduleId).orElseThrow(() -> new EntityNotFoundException(Module.class));
        module.getLessons().add(textLesson);
        moduleRepository.save(module);

        return mapper.convertValue(createdTextLesson, TextLessonDTO.class);
    }

    public TextLessonDTO update(TextLessonDTO textLessonDTO, String id) {

        verifyFileUrl(textLessonDTO);

        var textLesson = textLessonRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(TextLesson.class));

        textLesson.setTitle(textLessonDTO.getTitle());
        textLesson.setDescription(textLessonDTO.getDescription());
        textLesson.setPdfUrl(textLessonDTO.getPdfUrl());
        textLesson.setThumbnailUrl(textLessonDTO.getThumbnailUrl());
        textLesson.setAnimationUrl(textLessonDTO.getAnimationUrl());

        var updatedTextLesson = textLessonRepository.save(textLesson);

        return mapper.convertValue(updatedTextLesson, TextLessonDTO.class);
    }

    public TextLessonDTO patch(TextLessonDTO textLessonDTO, String id) {

        var textLesson = textLessonRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(TextLesson.class));

        if (textLessonDTO.getTitle() != null) textLesson.setTitle(textLessonDTO.getTitle());
        if (textLessonDTO.getDescription() != null) textLesson.setDescription(textLessonDTO.getDescription());

        if (textLessonDTO.getPdfUrl() != null) {
            verifyPdfUrl(textLessonDTO);
            textLesson.setPdfUrl(textLessonDTO.getPdfUrl());
        }

        if (textLessonDTO.getThumbnailUrl() != null) {
            verifyThumbnailUrl(textLessonDTO);
            textLesson.setThumbnailUrl(textLessonDTO.getThumbnailUrl());
        }

        if (textLessonDTO.getAnimationUrl() != null) {
            verifyAnimationUrl(textLessonDTO);
            textLesson.setAnimationUrl(textLessonDTO.getAnimationUrl());
        }

        var patchedTextLesson = textLessonRepository.save(textLesson);

        return mapper.convertValue(patchedTextLesson, TextLessonDTO.class);
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

    private void verifyPdfUrl(TextLessonDTO textLessonDTO) {
        if (!FileExtensionValidator.validatePdfFileExtension(textLessonDTO.getPdfUrl())) throw new InvalidVideoException(textLessonDTO.getPdfUrl());
    }

    private void verifyFileUrl(@NotNull TextLessonDTO textLessonDTO) {
        verifyPdfUrl(textLessonDTO);
        verifyThumbnailUrl(textLessonDTO);
        verifyAnimationUrl(textLessonDTO);
    }
}
