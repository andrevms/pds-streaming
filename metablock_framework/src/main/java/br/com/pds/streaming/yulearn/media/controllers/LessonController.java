package br.com.pds.streaming.yulearn.media.controllers;

import br.com.pds.streaming.yulearn.media.model.dto.LessonDTO;
import br.com.pds.streaming.yulearn.media.services.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @GetMapping
    public ResponseEntity<List<LessonDTO>> getAllLessons() {
        return new ResponseEntity<>(lessonService.findAll(), HttpStatus.OK);
    }
}
