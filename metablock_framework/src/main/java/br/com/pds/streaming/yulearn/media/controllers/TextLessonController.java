package br.com.pds.streaming.yulearn.media.controllers;

import br.com.pds.streaming.yulearn.media.model.dto.TextLessonDTO;
import br.com.pds.streaming.yulearn.media.services.TextLessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/textlessons", "/api/text-lessons", "/api/text_lessons"})
public class TextLessonController {

    @Autowired
    private TextLessonService textLessonService;

    @GetMapping
    public ResponseEntity<List<TextLessonDTO>> getAllTextLessons() {
        return new ResponseEntity<>(textLessonService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TextLessonDTO> getTextLessonById(@PathVariable String id) {
        return new ResponseEntity<>(textLessonService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TextLessonDTO> createTextLesson(@RequestBody TextLessonDTO textLessonDTO, @RequestParam(name = "moduleId") String moduleId) {
        return new ResponseEntity<>(textLessonService.insert(textLessonDTO, moduleId), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TextLessonDTO> updateTextLesson(@RequestBody TextLessonDTO textLessonDTO, @PathVariable String id) {
        return new ResponseEntity<>(textLessonService.update(textLessonDTO, id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TextLessonDTO> patchTextLesson(@RequestBody TextLessonDTO textLessonDTO, @PathVariable String id) {
        return new ResponseEntity<>(textLessonService.patch(textLessonDTO, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTextLesson(@PathVariable String id) {
        textLessonService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
