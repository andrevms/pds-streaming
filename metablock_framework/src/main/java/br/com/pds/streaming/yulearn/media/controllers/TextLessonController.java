package br.com.pds.streaming.yulearn.media.controllers;

import br.com.pds.streaming.yulearn.media.model.dto.TextLessonRequest;
import br.com.pds.streaming.yulearn.media.model.dto.TextLessonResponse;
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
    public ResponseEntity<List<TextLessonResponse>> getAllTextLessons() {
        return new ResponseEntity<>(textLessonService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TextLessonResponse> getTextLessonById(@PathVariable String id) {
        return new ResponseEntity<>(textLessonService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TextLessonResponse> createTextLesson(@RequestBody TextLessonRequest textLessonRequest, @RequestParam(name = "moduleId") String moduleId) {
        return new ResponseEntity<>(textLessonService.insert(textLessonRequest, moduleId), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TextLessonResponse> updateTextLesson(@RequestBody TextLessonRequest textLessonRequest, @PathVariable String id) {
        return new ResponseEntity<>(textLessonService.update(textLessonRequest, id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TextLessonResponse> patchTextLesson(@RequestBody TextLessonRequest textLessonRequest, @PathVariable String id) {
        return new ResponseEntity<>(textLessonService.patch(textLessonRequest, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTextLesson(@PathVariable String id) {
        textLessonService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
